import controller.KeyboardController;
import sas.View;

public class Main {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;

    public static void main(String[] args) {

        View view = new View(WIDTH, HEIGHT);
        KeyboardController controller = new KeyboardController(view);
        ViewContents menus = new ViewContents(view, controller);
    }
}