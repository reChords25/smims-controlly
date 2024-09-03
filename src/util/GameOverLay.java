package util;

import controller.AbstractController;
import sas.Rectangle;
import sas.Sprite;
import sas.View;
import viewcontents.AbstractViewContent;

import java.awt.*;
import java.util.ArrayList;

public class GameOverLay extends AbstractViewContent {

    private static final int PADDING = 25;
    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTON_FS = 50;

    private Button restartButton;
    private Button exitButton;
    private Button menuButton;
    private Rectangle mainRect;

    public GameOverLay(View view, AbstractController controller, int centerX, int centerY, int width, int height){
        super(view, controller);
    }

    @Override
    protected void initView() {
        int width = 500;
        int height = 300;
        int xPos = view.getWidth() / 2 - width / 2;
        int yPos = view.getHeight() / 2 - height / 2;

        mainRect = new Rectangle(xPos, yPos, width, height);
        mainRect.setColor(Color.LIGHT_GRAY);
        restartButton = new Button(xPos + PADDING, yPos + height - 2*PADDING - 2*BUTTON_HEIGHT, width - 2 * PADDING, BUTTON_HEIGHT, "Restart game", Color.GREEN);
        menuButton = new Button(xPos + PADDING, yPos + height - BUTTON_HEIGHT - PADDING, (int) (width / 2 - 1.5 * PADDING), BUTTON_HEIGHT, "Return to menu", Color.RED);
        exitButton = new Button((int) (xPos + 2*PADDING + menuButton.getWidth()), yPos + height - BUTTON_HEIGHT - PADDING, (int) (width / 2 - 1.5 * PADDING), BUTTON_HEIGHT, "Exit game", Color.RED);

        buttonsToRemove.add(restartButton);
        buttonsToRemove.add(menuButton);
        buttonsToRemove.add(exitButton);
        shapesToRemove.add(mainRect);
    }

    @Override
    public boolean tick() {
        if (exitButton.clicked()) {
            System.exit(0);
        }
        return true;
    }
}
