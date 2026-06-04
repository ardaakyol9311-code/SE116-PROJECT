package cells;

public abstract class Zone extends Cell {
    protected int level;
    protected int electricity;
    protected int water;
    protected int internet;
    protected int population;
    protected int goods;
    protected int lifestyle;
    protected boolean security;
    protected boolean health;
    protected boolean education;
    protected int generatedLastTick;

    public Zone(int row, int col, char symbol) {
        super(row, col, symbol);
        level = 0;
        generatedLastTick = 1;
    }

    @Override
    public boolean isZone() {
        return true;
    }

    @Override
    public boolean isConnectable() {
        return true;
    }
    public void resetTickData() {
        electricity = 0;
        water = 0;
        internet = 0;
        population = 0;
        goods = 0;
        lifestyle = 0;
        security = false;
        health = false;
        education = false;
    }
    public int getLevel() {
        return level;
    }
    public int getGeneratedLastTick() {
        return generatedLastTick;
    }
    public void receiveUtility(String type, int amount) {
        if(type.equals("electricity")) {
            electricity += amount;
        }
        else if(type.equals("water")) {
            water += amount;
        }
        else if(type.equals("internet")) {
            internet += amount;
        }
        System.out.println(
                getName() +
                        " at (" + row + "," + col + ")" +
                        " received " + amount +
                        " " + type
        );
    }
    public void receivePopulation(int amount) {
        if(amount <= 0) return;
        population += amount;
        System.out.println(
                getName() +
                        " at (" + row + "," + col + ")" +
                        " received " + amount +
                        " population"
        );
    }
    public void receiveGoods(int amount) {
        if(amount <= 0) return;
        goods += amount;
        System.out.println(
                getName() +
                        " at (" + row + "," + col + ")" +
                        " received " + amount +
                        " goods"
        );
    }
    public void receiveLifestyle(int amount) {
        if(amount <= 0) return;
        lifestyle += amount;
        System.out.println(
                getName() +
                        " at (" + row + "," + col + ")" +
                        " received " + amount +
                        " lifestyle"
        );
    }
    public void receiveService(String type) {
        if(type.equals("security")) {
            security = true;
        }
        else if(type.equals("health")) {
            health = true;
        }
        else if(type.equals("education")) {
            education = true;
        }
        System.out.println(
                getName() +
                        " at (" + row + "," + col + ")" +
                        " received " + type + " service"
        );
    }
    protected int baseProduction(int m) {
        if(level == 0) return 0;
        if(level == 1) return m;
        if(level == 2) return 2 * m;

        return 2 * m;
    }
    protected void levelUp() {

        if(level < 3) {
            int oldLevel = level;
            level++;
            System.out.println(
                    getName() +
                            " at (" + row + "," + col + ")" +
                            " levels up from " +
                            oldLevel + " to " + level
            );
        }
    }
    protected void levelDown() {
        if(level > 0) {
            int oldLevel = level;
            level--;
            System.out.println(
                    getName() +
                            " at (" + row + "," + col + ")" +
                            " levels down from " +
                            oldLevel + " to " + level
            );
        }
    }
    public abstract int generate();
}