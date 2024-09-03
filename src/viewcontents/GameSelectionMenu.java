package viewcontents;


import sas.Text;
import sas.View;

import controller.AbstractController;
import util.Button;
import viewcontents.tetris.TetrisGame;

import java.awt.*;

public class GameSelectionMenu extends AbstractViewContent {

    private Text mainText;

    private Button tetrisGameButton;
    private Button chessGameButton;

    public GameSelectionMenu(View view, AbstractController controller) {
        super(view, controller);
    }

    @Override
    protected void initView() {
        int viewCenterX = view.getWidth() / 2;
        int viewCenterY = view.getHeight() / 2;
        int buttonWidth = 400;
        int buttonHeight = 50;

        mainText = new Text(0, 0, "Select a game:", Color.BLACK);
        mainText.setFontSansSerif(true, 40);
        mainText.moveTo(viewCenterX - mainText.getShapeWidth() / 2, 100);

        tetrisGameButton = new Button(
                viewCenterX - buttonWidth / 2,
                175,
                buttonWidth,
                buttonHeight,
                "Tetris",
                new Color(85, 85, 255)
        );

        chessGameButton = new Button(
                viewCenterX - buttonWidth / 2,
                250,
                buttonWidth,
                buttonHeight,
                "Chess",
                new Color(57, 243, 91)
        );

        buttonsToRemove.add(tetrisGameButton);
        buttonsToRemove.add(chessGameButton);
        textsToRemove.add(mainText);
    }

    @Override
    public boolean run() {
        if (tetrisGameButton.clicked()) {
            ViewContents.getInstance().clear();
            ViewContents.getInstance().setViewContent(new TetrisGame(view, controller));
            return false;
        }
        if (view.keyPressed((char) 8)) {
            ViewContents.getInstance().clear();
            ViewContents.getInstance().setViewContent(new MainMenu(view, controller));
            return false;
        }
        return true;
    }


}
