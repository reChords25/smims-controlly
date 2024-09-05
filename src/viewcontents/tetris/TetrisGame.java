package viewcontents.tetris;

import controller.AbstractController;
import sas.Rectangle;
import sas.View;
import util.Button;
import viewcontents.AbstractViewContent;

import java.awt.*;
import java.util.Random;

public class TetrisGame extends AbstractViewContent {

    private static final Color TRANSPARENT = new Color(255, 255, 255, 0);
    Button but;
    private Rectangle[][] rects;
    private TetrisElement currentElement;
    private boolean running;


    public TetrisGame(View view, AbstractController controller) {
        super(view, controller);
        this.running = true;
    }

    @Override
    protected void initView() {
        Random rand = new Random();
        rects = new Rectangle[10][18];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 18; j++) {
                rects[i][j] = new Rectangle(i * 30 + 80, j * 30 + 80, 30, 30);
                shapesToRemove.add(rects[i][j]);
            }
        }
        currentElement = TetrisElement.J;
        currentElement.setGameField(rects);
    }

    @Override
    public boolean tick() {
        System.out.println("Tick.");
        if (controller.getLJoystickY() > 0) {
            currentElement.goDown();
        }
        if (controller.getLJoystickX() > 0) {
            currentElement.goRight();
        } else {
            currentElement.goLeft();
        }
//        if (view.keyPressed()) {
//            System.out.println("Key pressed.");
//            switch ((int) view.keyGetChar()) {
//                case 37: // Arrow left
//                    System.out.println("Arrow left.");
//                    currentElement.goLeft();
//                    break;
//                case 38: // Arrow up
//                    System.out.println("Arrow up.");
//                    currentElement.rotate();
//                    break;
//                case 39: // Arrow right
//                    System.out.println("Arrow right.");
//                    currentElement.goRight();
//                    break;
//                case 40: // Arrow down
//                    System.out.println("Arrow down.");
//                    currentElement.goDown();
//                    break;
//                default:
//                    System.out.println("Unknown key.");
//            }
//        }
//            GameOverLay overlay = new GameOverLay(view, controller);
//            overlay.setGameData(this.getClass(), false);
//            ViewContents.getInstance().runViewContent(overlay);

        return true;
    }

    private void draw() {
        currentElement.draw();
    }

    private void deleteFullLines() {
        // Iterate through all rows
        for (int i = 0; i < rects.length; i++) {
            // Iterate through all columns
            for (int j = 0; j < rects[0].length; j++) {
                // Check whether column is "empty"
                // If not, go to next column
                if (rects[i][j].getColor().getTransparency() == 0) {
                    break;
                }
            }

            // Else, move everything above one step down
            for (int k = 0; k < i; k++) {
                for (int j = 0; j < rects[0].length; j++) {
                    rects[k + 1][j].setColor(rects[k][j].getColor());
                }
            }
        }
    }
}
