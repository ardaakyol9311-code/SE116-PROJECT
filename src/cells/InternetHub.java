package cells;

public class InternetHub extends UtilityProvider {

    public InternetHub(int row, int col) {
        super(row, col, 'T', "internet");
    }

    @Override
    public String getName() {
        return "Internet Hub";
    }
}