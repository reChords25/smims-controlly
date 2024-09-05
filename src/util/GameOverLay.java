package util;

import controller.AbstractController;
import sas.Rectangle;
import sas.Text;
import sas.View;
import viewcontents.AbstractViewContent;
import viewcontents.MainMenu;
import viewcontents.ViewContents;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class GameOverLay extends AbstractViewContent {

    private static final int PADDING = 25;
    private static final int BUTTON_HEIGHT = 50;

    private Class<? extends AbstractViewContent> gameClass;

    private Text gameStatusText;

    private Button restartButton;
    private Button exitButton;
    private Button menuButton;
    private Rectangle mainRect;

    private boolean hasWon;

    public GameOverLay(View view, AbstractController controller) {
        super(view, controller);
    }

    @Override
    protected void initView() {
        int width = 500;
        int height = 300;
        int xPos = view.getWidth() / 2 - width / 2;
        int yPos = view.getHeight() / 2 - height / 2;

        mainRect = new Rectangle(xPos, yPos, width, height);
        mainRect.setColor(new Color(212, 247, 224));

        String status = "Placeholder";
        gameStatusText = new Text(340, 250, status);
        gameStatusText.setFontSansSerif(true, 50);
        gameStatusText.moveTo(xPos + (width - gameStatusText.getShapeWidth()) / 2, yPos + 50);

        restartButton = new Button(
                xPos + PADDING,
                yPos + height - 2*PADDING - 2*BUTTON_HEIGHT,
                width - 2 * PADDING,
                BUTTON_HEIGHT,
                "Restart game",
                new Color(119, 220, 54)
        );

        menuButton = new Button(
                xPos + PADDING,
                yPos + height - BUTTON_HEIGHT - PADDING,
                (int) Math.round(width / 2d - 1.5 * PADDING),
                BUTTON_HEIGHT,
                "To menu",
                new Color(142, 235, 174)
        );

        exitButton = new Button(
                (int) Math.round(xPos + 2*PADDING + menuButton.getWidth()),
                yPos + height - BUTTON_HEIGHT - PADDING,
                (int) Math.round(width / 2d - 1.5 * PADDING),
                BUTTON_HEIGHT,
                "Exit game",
                new Color(83, 225, 183)
        );

        buttonsToRemove.add(restartButton);
        buttonsToRemove.add(menuButton);
        buttonsToRemove.add(exitButton);
        textsToRemove.add(gameStatusText);
        shapesToRemove.add(mainRect);
    }

    @Override
    public boolean tick() {
        if (restartButton.clicked()) {
            try {
                ViewContents.getInstance().clear(2);
                ViewContents.getInstance().runViewContent(gameClass.getDeclaredConstructor(View.class, AbstractController.class).newInstance(view, controller));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                System.out.println("Exception: " + e);
            }
            return false;
        }
        if (menuButton.clicked()) {
            ViewContents.getInstance().clear(2);
            ViewContents.getInstance().runViewContent(new MainMenu(view, controller));
            return false;
        }
        if (exitButton.clicked()) {
            System.exit(0);
        }
        return true;
    }

    public void setGameData(Class<? extends AbstractViewContent> gameClass, boolean hasWon) {
        this.hasWon = hasWon;
        this.gameClass = gameClass;
        gameStatusText.setText(hasWon ? "You won!" : "You lost!");
    }
}
