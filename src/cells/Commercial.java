package cells;

public class Commercial extends Zone {

    public Commercial(int row, int col) {
        super(row, col, 'C');
    }

    @Override
    public String getName() {
        return "Commercial";
    }

    @Override
    public int generate() {
        int oldLevel = level;
        boolean hasUtilities = electricity > 0 && water > 0 && internet > 0;
        if (!hasUtilities) {
            level = 0;
        } else {
            int q = 1;
            if (security) {
                if (population > 0 && goods > 0) {
                    q = 3;
                } else {
                    q = 2;
                }
            }

            if (q > level)
                level++;
            else if (q < level)
                level--;
        }
        int produced = 0;
        if (hasUtilities) {
            int m = Math.min(electricity, Math.min(water, internet));
            produced = baseProduction(m);
            if (level == 3) produced += Math.min(population, goods);
        }
        generatedLastTick = produced;

        System.out.println("Commercial at (" + row + "," + col + ") generated " + produced + " lifestyle");
        if (level > oldLevel)
            System.out.println("Commercial at (" + row + "," + col + ") levels up from " + oldLevel + " to " + level);
        else if (level < oldLevel)
            System.out.println("Commercial at (" + row + "," + col + ") levels down from " + oldLevel + " to " + level);
        return produced;
    }
}