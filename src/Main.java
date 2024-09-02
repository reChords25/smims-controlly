import controller.KeyboardController;
import menu.MainMenu;
import minigames.frogger.FroggerGame;
import sas.View;

public class Main {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;

    public static void main(String[] args) {

        View view = new View(WIDTH, HEIGHT);
        KeyboardController controller = new KeyboardController(view);
        MainMenu menu = new MainMenu(view, controller);

        menu.run();
    }
}