package util;

import sas.Rectangle;
import sas.Sprite;

import java.awt.*;

public class GameOverSprite {

    private static final int PADDING = 20;
    private static final int BUTTON_HEIGHT = 40;

    private Sprite sprite;
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    public GameOverSprite(int centerX, int centerY, int width, int height){
        this.xPos = centerX - width / 2;
        this.yPos = centerY - height / 2;
        this.height = height;
        this.width = width;

        Rectangle mainRect = new Rectangle(xPos, yPos, width, height);
        mainRect.setColor(Color.LIGHT_GRAY);

        Button restartButton = new Button(xPos + PADDING, yPos + height - 2*PADDING - 2*BUTTON_HEIGHT, width - 2 * PADDING, BUTTON_HEIGHT, "Restart game", Color.GREEN);
        Button menuButton = new Button(xPos + PADDING, yPos + height - BUTTON_HEIGHT - PADDING, (int) (width / 2 - 1.5 * PADDING), BUTTON_HEIGHT, "Return to menu", Color.RED);
        Button exitButton = new Button((int) (xPos + 2*PADDING + menuButton.getWidth()), yPos + height - BUTTON_HEIGHT - PADDING, (int) (width / 2 - 1.5 * PADDING), BUTTON_HEIGHT, "Return to menu", Color.RED);
    }
}
