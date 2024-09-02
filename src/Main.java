import controller.KeyboardController;
import minigames.frogger.FroggerGame;
import sas.View;

public class Main {

    public static void main(String[] args) {

/*
        SaSDemo demo = new SaSDemo();
        demo.demo1();
        demo.demo2();
*/


        View view = new View();
        KeyboardController controller = new KeyboardController(view);
        FroggerGame game = new FroggerGame(controller, view);

        game.runGame();
    }
}