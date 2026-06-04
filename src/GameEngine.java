import cells.*;

import java.util.ArrayDeque;
import java.util.Queue;

public class GameEngine {

    private CityMap cityMap;

    private int storedPopulation;
    private int storedGoods;
    private int storedLifestyle;

    public GameEngine(CityMap cityMap) {
        this.cityMap = cityMap;

        storedPopulation = 0;
        storedGoods = 0;
        storedLifestyle = 0;
    }


    public void run(int ticks) {
        for (int i = 1; i <= ticks; i++) {
            System.out.println("Tick " + i);
            runOneTick();
        }
    }

    private void runOneTick() {
        resetZones();

        provideServices();

        distributeUtilities();

        distributeStoredResources();

        produceNewResources();
    }

    private void resetZones() {
        for (Zone zone : cityMap.getZones()) {
            zone.resetTickData();
        }
    }

    private void provideServices() {
        for (ServiceBuilding serviceBuilding : cityMap.getServiceBuildings()) {
            for (Zone zone : cityMap.getZones()) {

                double distance = distance(
                        serviceBuilding.getRow(),
                        serviceBuilding.getCol(),
                        zone.getRow(),
                        zone.getCol()
                );

                if (distance <= serviceBuilding.getRadius()) {
                    zone.receiveService(serviceBuilding.getServiceType());
                }
            }
        }
    }

    private double distance(int r1, int c1, int r2, int c2) {
        int rowDiff = r1 - r2;
        int colDiff = c1 - c2;

        return Math.sqrt(rowDiff * rowDiff + colDiff * colDiff);
    }

    private void distributeUtilities() {
        for (UtilityProvider provider : cityMap.getUtilityProviders()) {
            distributeUtilityWithBFS(provider);
        }
    }

    private void distributeUtilityWithBFS(UtilityProvider provider) {

        int remaining = provider.getCapacity();

        boolean[][] visited = new boolean[cityMap.getRows()][cityMap.getCols()];

        Queue<int[]> queue = new ArrayDeque<>();

        queue.add(new int[]{provider.getRow(), provider.getCol()});
        visited[provider.getRow()][provider.getCol()] = true;

        int[] rowChange = {1, -1, 0, 0};
        int[] colChange = {0, 0, 1, -1};

        while (!queue.isEmpty() && remaining > 0) {

            int[] current = queue.poll();

            int row = current[0];
            int col = current[1];

            Cell cell = cityMap.getCell(row, col);

            if (cell instanceof Zone) {
                Zone zone = (Zone) cell;

                int demand = calculateUtilityDemand(zone);

                int given = Math.min(demand, remaining);

                if (given > 0) {
                    zone.receiveUtility(provider.getUtilityType(), given);
                    remaining -= given;
                }
            }

            for (int i = 0; i < 4; i++) {

                int nextRow = row + rowChange[i];
                int nextCol = col + colChange[i];

                if (!cityMap.isInside(nextRow, nextCol)) {
                    continue;
                }

                if (visited[nextRow][nextCol]) {
                    continue;
                }

                Cell nextCell = cityMap.getCell(nextRow, nextCol);

                if (!nextCell.isConnectable()) {
                    continue;
                }

                visited[nextRow][nextCol] = true;
                queue.add(new int[]{nextRow, nextCol});
            }
        }
    }

    private int calculateUtilityDemand(Zone zone) {
        int produced = zone.getGeneratedLastTick();

        if (produced <= 0) {
            return 1;
        }

        return produced;
    }

    private void distributeStoredResources() {

        int workZoneCount =
                cityMap.getIndustrials().size() +
                        cityMap.getCommercials().size();

        if (workZoneCount > 0 && storedPopulation > 0) {
            int populationPerZone = storedPopulation / workZoneCount;

            for (Commercial commercial : cityMap.getCommercials()) {
                commercial.receivePopulation(populationPerZone);
            }

            for (Industrial industrial : cityMap.getIndustrials()) {
                industrial.receivePopulation(populationPerZone);
            }
        }

        if (!cityMap.getCommercials().isEmpty() && storedGoods > 0) {
            int goodsPerCommercial =
                    storedGoods / cityMap.getCommercials().size();

            for (Commercial commercial : cityMap.getCommercials()) {
                commercial.receiveGoods(goodsPerCommercial);
            }
        }

        if (!cityMap.getHouses().isEmpty() && storedLifestyle > 0) {
            int lifestylePerHouse =
                    storedLifestyle / cityMap.getHouses().size();

            for (Housing housing : cityMap.getHouses()) {
                housing.receiveLifestyle(lifestylePerHouse);
            }
        }
    }

    private void produceNewResources() {

        int newPopulation = 0;
        int newGoods = 0;
        int newLifestyle = 0;

        for (Zone zone : cityMap.getZones()) {

            int produced = zone.generate();

            if (zone instanceof Housing) {
                newPopulation += produced;
            } else if (zone instanceof Industrial) {
                newGoods += produced;
            } else if (zone instanceof Commercial) {
                newLifestyle += produced;
            }
        }

        storedPopulation = newPopulation;
        storedGoods = newGoods;
        storedLifestyle = newLifestyle;
    }
}