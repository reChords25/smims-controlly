package viewcontents.tetris;

import controller.AbstractController;
import sas.View;
import util.GameOverSprite;
import viewcontents.AbstractViewContent;
import viewcontents.ViewContents;

public class TetrisGame extends AbstractViewContent {

    public TetrisGame(View view, AbstractController controller) {
        super(view, controller);
    }

    @Override
    protected void initView() {
        GameOverSprite o = new GameOverSprite(view.getWidth() / 2, view.getHeight() / 2, 600, 400);
    }

    @Override
    public boolean run() {
        return false;
    }
}
