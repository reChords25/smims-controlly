package viewcontents.tetris;

import controller.AbstractController;
import sas.View;
import util.Button;
import util.GameOverLay;
import viewcontents.AbstractViewContent;
import viewcontents.ViewContents;

import java.awt.*;

public class TetrisGame extends AbstractViewContent {

    Button but;

    public TetrisGame(View view, AbstractController controller) {
        super(view, controller);
    }

    @Override
    protected void initView() {
        but = new Button(50, 400, 300, 50, "hallo", Color.CYAN);
        buttonsToRemove.add(but);
    }

    @Override
    public boolean tick() {
        if (but.clicked()) {
            ViewContents.getInstance().runViewContent(new GameOverLay(view, controller, this.getClass(), false));
            return false;
        }
        return true;
    }
}
