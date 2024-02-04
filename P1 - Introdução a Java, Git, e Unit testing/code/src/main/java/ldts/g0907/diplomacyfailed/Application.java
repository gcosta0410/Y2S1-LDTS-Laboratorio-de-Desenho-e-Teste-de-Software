package ldts.g0907.diplomacyfailed;

public class Application {
    public static void main(String[] args) {
        Game game;
        game = new Game(new LanternaFactory(100, 50));
        game.run();
    }
}
