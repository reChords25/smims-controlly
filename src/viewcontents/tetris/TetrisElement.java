package viewcontents.tetris;

import sas.Rectangle;

import java.awt.Color;

public class TetrisElement {

    public static final TetrisElement I = new TetrisElement(new int[][]{{1, 1, 1, 1}}, Color.CYAN);
    public static final TetrisElement J = new TetrisElement(new int[][]{{1, 0, 0}, {1, 1, 1}}, Color.PINK);
    public static final TetrisElement L = new TetrisElement(new int[][]{{0, 0, 1}, {1, 1, 1}}, Color.ORANGE);
    public static final TetrisElement O = new TetrisElement(new int[][]{{1, 1}, {1, 1}}, Color.YELLOW);
    public static final TetrisElement S = new TetrisElement(new int[][]{{0, 1, 1}, {1, 1, 0}}, Color.RED);
    public static final TetrisElement Z = new TetrisElement(new int[][]{{1, 1, 0}, {0, 1, 1}}, Color.GREEN);
    public static final TetrisElement T = new TetrisElement(new int[][]{{0, 1, 0}, {1, 1, 1}}, Color.MAGENTA);

    private Rectangle[][] rects;
    private int[][] shape; // 2D array to represent the shape
    private Color color;
    private int[] position; // Position of the piece

    private TetrisElement(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
        position = new int[2];
        position[0] = 6 - shape[0].length / 2;
    }

    public void setGameField(Rectangle[][] rects) {
        this.rects = rects;
    }

    // Draw the element on the grid
    public void draw() {
        // Rows
        for (int i = 0; i < shape.length; i++) {
            // Columns
            for (int j = 0; j < shape[i].length; j++) {
                // Fill rectangle with shape color, if point in matrix is 1
                if (shape[i][j] == 1) {
                    rects[position[0] + i][position[1] + j].setColor(color); // Set color visible
                }
            }
        }
    }

    // Move down, return false if it can't move
    public boolean goDown() {
        position[1]++;
        return willCollide("down");
    }

    // Move left
    public void goLeft() {
        position[0]--;
        willCollide("left");
    }

    // Move right
    public void goRight() {
        position[0]++;
        willCollide("right");
    }

    // Rotate the piece
    public void rotate() {
        int[][] temp = new int[shape[0].length][shape.length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                temp[i][j] = shape[j][temp[0].length - i];
            }
        }
        shape = temp;
    }

    private boolean willCollide(String direction) {
        switch (direction) {
            case "down":
                if (position[0] + shape.length > rects.length) {
                    return true;
                }
                break;
            case "left":
                if (position[1] + shape[0].length > rects[0].length) {
                    return true;
                }
                break;
            default:
                break;
        }
        return willCollideOtherElement(direction);
    }

    private boolean willCollideOtherElement(String direction) {
        switch (direction) {
            case "down":
                for (int i = shape.length - 1; i >= 0; i--) {
                    for (int j = 0; j < shape[0].length; j++) {
                        if (shape[i][j] == 1 && rects[i][j].getColor().getTransparency() == 1) {
                            return true;
                        }
                    }
                }
                break;
            case "left":
                for (int i = 0; i < shape[0].length; i++) {
                    for (int j = 0; j < shape.length; j++) {
                        if (shape[i][j] == 1 && rects[i][j].getColor().getTransparency() == 1) {
                            return true;
                        }
                    }
                }
                break;
            case "right":
                for (int i = shape[0].length - 1; i >= 0; i--) {
                    for (int j = 0; j < shape.length; j++) {
                        if (shape[i][j] == 1 && rects[i][j].getColor().getTransparency() == 1) {
                            return true;
                        }
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }
}
