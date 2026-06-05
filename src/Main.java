public class Main {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage: java Main <mapfile> <ticks>");
            return;
        }

        try {
            String mapFile = args[0];
            int ticks = Integer.parseInt(args[1]);

            CityMap cityMap = new CityMap(mapFile);
            GameEngine engine = new GameEngine(cityMap);

            engine.run(ticks);

        } catch (NumberFormatException e) {
            System.out.println("Tick count must be an integer.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
