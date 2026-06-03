package cells;

public class Hospital extends ServiceBuilding {

    public Hospital(int row, int col) {
        super(row, col, 'D', "health", 3);
    }

    @Override
    public String getName() {
        return "Hospital";
    }
}