package viewcontents.snake;

import controller.AbstractController;
import sas.Circle;
import sas.Rectangle;
import sas.Tools;
import sas.View;
import util.GameOverLay;
import viewcontents.AbstractViewContent;
import viewcontents.ViewContents;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class SnakeGame extends AbstractViewContent {

    private static final int SQR_SIZE = 30;
    private static final int FIELD_SIZE = 18;
    private static final int SNAKE_SIZE = 20;
    private static final int TICKS_TO_MOVE = 50;
    private static final double THRESHOLD_X_LEFT = -1.0; // TODO 200
    private static final double THRESHOLD_X_RIGHT = 1.0; // TODO 800
    private static final double THRESHOLD_Y_DOWN = 1.0; // TODO 200
    private static final double THRESHOLD_Y_UP = -1.0; // TODO 800
    private static final int BLOCK_INPUT_TICKS = 5;

    private static Rectangle newSnakeSegment(double x, double y) {
        return new Rectangle(x, y, SNAKE_SIZE, SNAKE_SIZE, Color.YELLOW);
    }

    private boolean won, died;
    private Rectangle background, contor;
    private Circle apple1, Apple2;
    private Rectangle[][] fields;
    int offSetX, offSetY;
    private Snake snakeNew;
    private Direction lastDirection;
    private int tickCounter;
    private int blockInputCounter;
    private int score;
    private Rectangle scoutRect;
    private boolean grow;

    public SnakeGame(View view, AbstractController controller) {
        super(view, controller);
    }

    @Override
    protected void initView() {

        this.won = false;
        this.died = false;
        this.fields = new Rectangle[FIELD_SIZE][FIELD_SIZE];
        this.snakeNew = new Snake(5, 5);
        this.lastDirection = Direction.RIGHT;
        this.tickCounter = 0;
        this.blockInputCounter = 0;
        this.score = 0;
        this.scoutRect = newSnakeSegment(0, 0);
        this.grow = false;

        background = new Rectangle(0, 0, view.getWidth(), view.getHeight(), new Color(6, 131, 6));

        offSetX = view.getWidth() / 2 - FIELD_SIZE * SQR_SIZE / 2;
        offSetY = view.getHeight() / 2 - FIELD_SIZE * SQR_SIZE / 2;
        contor = new Rectangle(offSetX - 5, offSetY - 5, FIELD_SIZE * SQR_SIZE + 10, FIELD_SIZE * SQR_SIZE + 10, Color.BLACK);
        fields = new Rectangle[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i += 2) {
            for (int j = 0; j < FIELD_SIZE; j += 2) {
                fields[j][i] = new Rectangle(offSetX + j * SQR_SIZE, offSetY + i * SQR_SIZE, SQR_SIZE, SQR_SIZE, new Color(82, 193, 80));
                fields[j + 1][i] = new Rectangle(offSetX + (j + 1) * SQR_SIZE, offSetY + i * SQR_SIZE, SQR_SIZE, SQR_SIZE, new Color(77, 154, 58));
                fields[j][i + 1] = new Rectangle(offSetX + j * SQR_SIZE, offSetY + (i + 1) * SQR_SIZE, SQR_SIZE, SQR_SIZE, new Color(77, 154, 58));
                fields[j + 1][i + 1] = new Rectangle(offSetX + (j + 1) * SQR_SIZE, offSetY + (i + 1) * SQR_SIZE, SQR_SIZE, SQR_SIZE, new Color(82, 193, 80));
            }
        }


        // initialising Snake
        int targetIndexX = snakeNew.getIndexX();
        int targetIndexY = snakeNew.getIndexY();
        double newX, newY;
        for (int i = 0; i < 3; i++) {
            Rectangle targetField = fields[targetIndexX][targetIndexY];
            newX = (SQR_SIZE - SNAKE_SIZE) / 2 + targetField.getShapeX();
            newY = (SQR_SIZE - SNAKE_SIZE) / 2 + targetField.getShapeY();
            snakeNew.add(newSnakeSegment(newX, newY));
            targetIndexX--;
        }
        snakeNew.getFirst().setColor(Color.ORANGE);
        scoutRect.setHidden(true);
        scoutRect.setTransparency(0.1f);


        // food
        apple1 = new Circle(0, 0, SQR_SIZE / 2.0, Color.RED);
        placeFood();

        // Make sure all elements created here will be removed later.
        for (int i = 0; i < fields.length; i++) {
            shapesToRemove.addAll(Arrays.asList(fields[i]));
        }
        shapesToRemove.add(apple1);
        shapesToRemove.addAll(snakeNew);
        shapesToRemove.add(background);
        shapesToRemove.add(contor);
        shapesToRemove.add(scoutRect);

    }

    @Override
    public boolean tick() {

        if (died) {
            GameOverLay overlay = new GameOverLay(view, controller);
            overlay.setGameData(this.getClass(), false);
            ViewContents.getInstance().runViewContent(overlay);
            return false;
        }

        if (won) {
            GameOverLay overlay = new GameOverLay(view, controller);
            overlay.setGameData(this.getClass(), true);
            ViewContents.getInstance().runViewContent(overlay);
            return false;
        }

        if (score >= 20) {
            won = true;
        }

        // Do we have to move the snake by force?
        if (tickCounter >= TICKS_TO_MOVE) {
            move(lastDirection);
            tickCounter = 0;
        }

        tickCounter++;

        // Do we have to move the snake by input?
        double joyX = controller.getLJoystickX(); // TODO: Changeo to R
        double joyY = controller.getLJoystickY(); // TODO: Changeo to R

        if (blockInputCounter >= 1) {
            blockInputCounter++;
        }
        if (blockInputCounter > BLOCK_INPUT_TICKS) {
            blockInputCounter = 0;
        }

        // Did we receive any input from the player?
        boolean inputReceived = joyX <= THRESHOLD_X_LEFT || joyX >= THRESHOLD_X_RIGHT
                || joyY >= THRESHOLD_Y_DOWN || joyY <= THRESHOLD_Y_UP;
        boolean inputValid = false;

        if (blockInputCounter == 0 && inputReceived) {
            if (joyX <= THRESHOLD_X_LEFT && lastDirection != Direction.RIGHT) {
                lastDirection = Direction.LEFT;
                inputValid = true;
            }
            if (joyX >= THRESHOLD_X_RIGHT && lastDirection != Direction.LEFT) {
                lastDirection = Direction.RIGHT;
                inputValid = true;
            }
            if (joyY >= THRESHOLD_Y_DOWN && lastDirection != Direction.UP) {
                lastDirection = Direction.DOWN;
                inputValid = true;
            }
            if (joyY <= THRESHOLD_Y_UP && lastDirection != Direction.DOWN) {
                lastDirection = Direction.UP;
                inputValid = true;
            }

            if (inputValid) {
                move(lastDirection);
                blockInputCounter = 1;
                tickCounter = 0;
            }
        }

        return true;

    }

    void placeFood() {

        int x, y;
        Rectangle field;

        // Try placing the apple on random fields until we finally find one without a snake segment.
        outer:
        while (true) {

            x = Tools.randomNumber(0, fields.length - 1);
            y = Tools.randomNumber(0, fields[0].length - 1);

            field = fields[x][y];

            apple1.moveTo(field.getShapeX(), field.getShapeY());
            for (Rectangle segment : snakeNew) {
                if (apple1.intersects(segment)) {
                    continue outer;
                }
            }

            return;

        }

    }

    public void move(Direction direction) {

        int targetIndexX = snakeNew.getIndexX() + direction.getX(); // Compute the new horizontal index of the snake.
        int targetIndexY = snakeNew.getIndexY() + direction.getY(); // Compute the new vertical index of the snake.

        if (!checkOutOfBounds(targetIndexX, targetIndexY)) {
            died = true;
            return;
        }

        // Compute the new coordinates of the target location.
        Rectangle targetField = fields[targetIndexX][targetIndexY];
        Rectangle head = snakeNew.getFirst();
        double newX = (SQR_SIZE - head.getShapeWidth()) / 2 + targetField.getShapeX();
        double newY = (SQR_SIZE - head.getShapeHeight()) / 2 + targetField.getShapeY();

        // Move the 'scout' rectangle into position to check for collisions.
        scoutRect.moveTo(newX, newY);
        scoutRect.setHidden(false);

        // Are we growing the snake? If so, the new segment will spawn where the last segment is right now.
        // If not, we can simply ignore the last segment as it will move out of the way in any case.
        Rectangle temp = grow ? null : snakeNew.getLast();


        // Have we bitten our tail?
        try {
            for (Rectangle segment : snakeNew) {

                // Are we skipping the last segment?
                if (segment == temp) {
                    continue;
                }

                // Game over!
                if (scoutRect.intersects(segment)) {
                    died = true;
                    return;
                }

            }
        } finally {
            scoutRect.setHidden(true);
        }

        // Move each segment to its new location successively.
        double prevX = 0, prevY = 0;

        for (Rectangle segment : snakeNew) {
            prevX = segment.getShapeX();
            prevY = segment.getShapeY();

            segment.moveTo(newX, newY);

            newX = prevX;
            newY = prevY;
        }

        // Are we adding a segment?
        if (grow) {
            Rectangle segment = newSnakeSegment(prevX, prevY);
            snakeNew.add(segment);
            shapesToRemove.add(segment);
            grow = false;
        }

        // Update the snake's index.
        snakeNew.setIndexX(targetIndexX);
        snakeNew.setIndexY(targetIndexY);

        // So we're not game over yet. Have we just eaten some food?
        if (head.intersects(apple1)) {
            grow = true;
            score++;
            placeFood();
        }

    }

    private boolean checkOutOfBounds(int x, int y) {

        if (x < 0 || x >= fields.length) {
            return false;
        }
        if (y < 0 || y >= fields[0].length) {
            return false;
        }
        return true;

    }


}





