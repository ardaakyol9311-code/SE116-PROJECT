package cells;

public class PoliceStation extends ServiceBuilding {

    public PoliceStation(int row, int col) {
        super(row, col, 'F', "security", 5);
    }

    @Override
    public String getName() {
        return "Police Station";
    }
}