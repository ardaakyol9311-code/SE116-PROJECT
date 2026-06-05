package cells;

public abstract class UtilityProvider extends Cell {

    protected String utilityType;
    protected int capacity;

    public UtilityProvider(int row, int col, char symbol, String utilityType) {
        super(row, col, symbol);
        this.utilityType = utilityType;
        this.capacity = 100;
    }

    public String getUtilityType() {
        return utilityType;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isConnectable() {
        return false;
    }
}