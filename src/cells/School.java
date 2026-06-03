package cells;

public class School extends ServiceBuilding {

    public School(int row, int col) {
        super(row, col, 'S', "education", 4);
    }

    @Override
    public String getName() {
        return "School";
    }
}