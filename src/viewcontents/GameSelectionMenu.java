package viewcontents;


import sas.Text;
import sas.View;

import controller.AbstractController;
import util.Button;
import viewcontents.cornerthequeen.CornerTheQueenGame;
import viewcontents.demo.Demo;
import viewcontents.tetris.TetrisGame;

import java.awt.*;

public class GameSelectionMenu extends AbstractViewContent {

    private Text mainText;

    private Button demoButton;
    private Button tankGameButton;
    private Button queenGameButton;

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

        demoButton = new Button(
                viewCenterX - buttonWidth / 2,
                175,
                buttonWidth,
                buttonHeight,
                "DEMO",
                new Color(85, 85, 255)
        );

//        tankGameButton = new Button(
//                viewCenterX - buttonWidth / 2,
//                250,
//                buttonWidth,
//                buttonHeight,
//                "Tank",
//                new Color(57, 243, 91)
//        );

        queenGameButton = new Button(
               viewCenterX - buttonWidth / 2,
               250,
               buttonWidth,
               buttonHeight,
               "Queen",
               new Color(57, 243, 91)
        );


        buttonsToRemove.add(demoButton);
        buttonsToRemove.add(queenGameButton);
//        buttonsToRemove.add(tankGameButton);
        textsToRemove.add(mainText);
    }

    @Override
    public boolean tick() {
        if (demoButton.clicked()) {
            ViewContents.getInstance().clear(1);
            ViewContents.getInstance().runViewContent(new Demo(view, controller));
            return false;
        }
//        if (tankGameButton.clicked()) {
//            ViewContents.getInstance().clear();
//            ViewContents.getInstance().runViewContent(new TankGame(view, controller));
//            return false;
//        }

        if (queenGameButton.clicked()) {
            ViewContents.getInstance().clear(1);
            ViewContents.getInstance().runViewContent(new CornerTheQueenGame(view, controller));
            return false;
        }

        if (view.keyPressed((char) 8)) {
            ViewContents.getInstance().clear(1);
            ViewContents.getInstance().runViewContent(new MainMenu(view, controller));
            return false;
        }
        return true;
    }


}
