package viewcontents;


import sas.Text;
import sas.View;

import controller.AbstractController;
import util.Button;
import viewcontents.cornerthequeen.CornerTheQueenGame;
import viewcontents.snake.SnakeGame;
import viewcontents.demo.Demo;
import viewcontents.tank.CactiCatchGame;


import java.awt.Color;

public class GameSelectionMenu extends AbstractViewContent {

    private Text mainText;

    private Button demoButton;
    private Button tankGameButton;
    private Button queenGameButton;
    private Button snakeGameButton;
    private Button testButton;

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

        tankGameButton = new Button(
                viewCenterX - buttonWidth / 2,
                250,
                buttonWidth,
                buttonHeight,
                "Cacti Catch",
                new Color(85, 255, 85)
        );

        queenGameButton = new Button(
               viewCenterX - buttonWidth / 2,
               325,
               buttonWidth,
               buttonHeight,
               "Corner The Queen",
               new Color(255, 85, 85)
        );

        snakeGameButton = new Button(
                viewCenterX - buttonWidth / 2,
                325,
                buttonWidth,
                buttonHeight,
                "Snake",
                new Color(57, 243, 91));

        buttonsToRemove.add(demoButton);
        buttonsToRemove.add(queenGameButton);
        buttonsToRemove.add(snakeGameButton);
        buttonsToRemove.add(tankGameButton);
        textsToRemove.add(mainText);
    }

    @Override
    public boolean tick() {
        if (demoButton.clicked()) {
            ViewContents.getInstance().clear(1);
            ViewContents.getInstance().runViewContent(new Demo(view, controller));
            return false;
        }
        if (tankGameButton.clicked()) {
            ViewContents.getInstance().clear(1);
            ViewContents.getInstance().runViewContent(new CactiCatchGame(view, controller));
            return false;
        }

        if (queenGameButton.clicked()) {
            ViewContents.getInstance().clear(1);
            ViewContents.getInstance().runViewContent(new CornerTheQueenGame(view, controller));
            return false;
        }

        if (snakeGameButton.clicked()) {
            ViewContents.getInstance().clear(1);
            ViewContents.getInstance().runViewContent(new SnakeGame(view, controller));
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
