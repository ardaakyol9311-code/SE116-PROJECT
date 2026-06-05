package cells;

public class Housing extends Zone {

    public Housing(int row, int col) {
        super(row, col, 'H');
    }

    @Override
    public String getName() {
        return "House";
    }

    @Override
    public int generate() {
        int oldLevel = level;
        boolean hasUtilities = electricity > 0 && water > 0 && internet > 0;
        if (!hasUtilities) {
            level = 0;
        } else {
            int q = 1;
            if (security && health && education) {
                if (lifestyle > 0) {
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
            if (level == 3) produced += lifestyle;
        }
        generatedLastTick = produced;
        System.out.println("House at (" + row + "," + col + ") generated " + produced + " population");
        if (level > oldLevel)
            System.out.println("House at (" + row + "," + col + ") levels up from " + oldLevel + " to " + level);
        else if (level < oldLevel)
            System.out.println("House at (" + row + "," + col + ") levels down from " + oldLevel + " to " + level);
        return produced;
    }
}