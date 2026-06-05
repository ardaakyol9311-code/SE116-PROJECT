package cells;

public class Industrial extends Zone {

    public Industrial(int row, int col) {
        super(row, col, 'I');
    }

    @Override
    public String getName() {
        return "Industrial";
    }

    @Override
    public int generate() {
        int oldLevel = level;
        boolean hasUtilities = electricity > 0 && water > 0;


        if (!hasUtilities) {
            level = 0;
        } else {
            int q = 1;
            if (security) q = (population > 0) ? 3 : 2;
            if (q > level) level++;
            else if (q < level) level--;
        }


        int produced = 0;
        if (hasUtilities) {
            int m = Math.min(electricity, water);
            produced = baseProduction(m);
            if (level == 3) produced += population;
        }
        generatedLastTick = produced;


        System.out.println("Industrial at (" + row + "," + col + ") generated " + produced + " goods");
        if (level > oldLevel)
            System.out.println("Industrial at (" + row + "," + col + ") levels up from " + oldLevel + " to " + level);
        else if (level < oldLevel)
            System.out.println("Industrial at (" + row + "," + col + ") levels down from " + oldLevel + " to " + level);

        return produced;
    }
}