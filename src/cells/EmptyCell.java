package cells;

public class EmptyCell extends Cell {

    public EmptyCell(int row, int col) {
        super(row, col, 'E');
    }

    @Override
    public String getName() {
        return "Empty";
    }
}