import cells.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CityMap {

    private Cell[][] grid;
    private int rows;
    private int cols;

    private ArrayList<Zone> zones;
    private ArrayList<Housing> houses;
    private ArrayList<Industrial> industrials;
    private ArrayList<Commercial> commercials;
    private ArrayList<UtilityProvider> utilityProviders;
    private ArrayList<ServiceBuilding> serviceBuildings;

    public CityMap(String fileName) throws Exception {

        ArrayList<String> lines = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (!line.isEmpty()) {
                lines.add(line);
            }
        }

        reader.close();

        if (lines.isEmpty()) {
            throw new Exception("Map file is empty.");
        }

        rows = lines.size();
        cols = lines.get(0).length();

        grid = new Cell[rows][cols];

        zones = new ArrayList<>();
        houses = new ArrayList<>();
        industrials = new ArrayList<>();
        commercials = new ArrayList<>();
        utilityProviders = new ArrayList<>();
        serviceBuildings = new ArrayList<>();

        buildMap(lines);
    }

    private void buildMap(ArrayList<String> lines) throws Exception {

        for (int r = 0; r < rows; r++) {

            if (lines.get(r).length() != cols) {
                throw new Exception("All map rows must have the same length.");
            }

            for (int c = 0; c < cols; c++) {

                char ch = lines.get(r).charAt(c);

                Cell cell = createCell(ch, r, c);

                grid[r][c] = cell;

                if (cell instanceof Zone) {
                    zones.add((Zone) cell);
                }

                if (cell instanceof Housing) {
                    houses.add((Housing) cell);
                }

                else if (cell instanceof Industrial) {
                    industrials.add((Industrial) cell);
                }

                else if (cell instanceof Commercial) {
                    commercials.add((Commercial) cell);
                }

                else if (cell instanceof UtilityProvider) {
                    utilityProviders.add((UtilityProvider) cell);
                }

                else if (cell instanceof ServiceBuilding) {
                    serviceBuildings.add((ServiceBuilding) cell);
                }
            }
        }
    }

    private Cell createCell(char ch, int row, int col) throws Exception {

        if (ch == 'E') return new EmptyCell(row, col);
        if (ch == 'R') return new Road(row, col);

        if (ch == 'H') return new Housing(row, col);
        if (ch == 'I') return new Industrial(row, col);
        if (ch == 'C') return new Commercial(row, col);

        if (ch == 'P') return new PowerPlant(row, col);
        if (ch == 'W') return new WaterPumpingStation(row, col);
        if (ch == 'T') return new InternetHub(row, col);

        if (ch == 'F') return new PoliceStation(row, col);
        if (ch == 'D') return new Hospital(row, col);
        if (ch == 'S') return new School(row, col);

        throw new Exception("Invalid map character: " + ch);
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public ArrayList<Zone> getZones() {
        return zones;
    }

    public ArrayList<Housing> getHouses() {
        return houses;
    }

    public ArrayList<Industrial> getIndustrials() {
        return industrials;
    }

    public ArrayList<Commercial> getCommercials() {
        return commercials;
    }

    public ArrayList<UtilityProvider> getUtilityProviders() {
        return utilityProviders;
    }

    public ArrayList<ServiceBuilding> getServiceBuildings() {
        return serviceBuildings;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean isInside(int row, int col) {
        return row >= 0 &&
                row < rows &&
                col >= 0 &&
                col < cols;
    }
}
