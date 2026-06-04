package cells;

public class PowerPlant extends UtilityProvider {

    public PowerPlant(int row, int col) {
        super(row, col, 'P', "electricity");
    }

    @Override
    public String getName() {
        return "Power Plant";
    }
}