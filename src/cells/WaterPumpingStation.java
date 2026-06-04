package cells;

public class WaterPumpingStation extends UtilityProvider {

    public WaterPumpingStation(int row, int col) {
        super(row, col, 'W', "water");
    }

    @Override
    public String getName() {
        return "Water Pumping Station";
    }
}