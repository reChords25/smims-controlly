package viewcontents.tetris;

import controller.AbstractController;
import sas.View;
import viewcontents.AbstractViewContent;

public class TetrisGame extends AbstractViewContent {
    public TetrisGame(View view, AbstractController controller) {
        super(view, controller);
    }

    @Override
    protected void initView() {

    }

    @Override
    public boolean run() {
        return true;
    }
}
