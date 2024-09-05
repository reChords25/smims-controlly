package viewcontents.cornerthequeen;

import controller.AbstractController;
import sas.Circle;
import sas.Rectangle;
import sas.View;
import util.GameOverLay;
import viewcontents.AbstractViewContent;
import viewcontents.ViewContents;

import java.awt.*;
import java.util.Random;


public class CornerTheQueenGame extends AbstractViewContent {
    private static final String PATH_TO_IMAGES = PATH_TO_RESOURCES + "cornerthequeen/";

    private int groesseFelder;
    private Rectangle[][] fields;
//    private int[][] losingFields = new int[11][2];
    private Circle queen;
    private Rectangle zeroField;

    private Circle markerTurn;
    private boolean isTurn, turnWasStarted, opponentTurn;

    int tickBuffer;

    public CornerTheQueenGame(View view, AbstractController controller) {
        super(view, controller);
    }

    @Override
    protected void initView() {
        tickBuffer = 10;
        isTurn = true;
        opponentTurn = false;
        fields = new Rectangle[30][30];
        if (view.getHeight() < view.getWidth()) {
            groesseFelder = (view.getHeight() / 30);
        } else {
            groesseFelder = (view.getWidth() / 30);
        }
        for (int i = 0; i < 30; i = i + 2) {
            for (int j = 0; j < 30; j = j + 2) {
                fields[j][i] = new Rectangle(j * groesseFelder, i * groesseFelder, groesseFelder, groesseFelder, Color.WHITE);
                fields[j + 1][i] = new Rectangle((j + 1) * groesseFelder, i * groesseFelder, groesseFelder, groesseFelder, Color.BLACK);
            }

            for (int j = 0; j < 30; j = j + 2) {
                fields[j][i + 1] = new Rectangle(j * groesseFelder, (i + 1) * groesseFelder, groesseFelder, groesseFelder, Color.BLACK);
                fields[j + 1][i + 1] = new Rectangle((j + 1) * groesseFelder, (i + 1) * groesseFelder, groesseFelder, groesseFelder, Color.WHITE);
            }
        }
        zeroField = new Rectangle(0, 29 * groesseFelder, groesseFelder, groesseFelder, Color.green);
        queen = new Circle(28 * groesseFelder, 0, (double) groesseFelder / 2, Color.pink);
        markerTurn = new Circle(queen.getCenterX() - queen.getShapeWidth() / 8, queen.getCenterY() - queen.getShapeHeight() / 8, queen.getShapeHeight() / 4, Color.lightGray);
        markerTurn.setHidden(true);
        //a & s as controls, enter for submit // joystick -> direction of joystick, button as submit
    }

    @Override
    public boolean tick() {
        if (view.keyPressed()) {
            if (isTurn && tickBuffer == 0) {
                if (!turnWasStarted) {
                    tickBuffer = 10;
                    turn();
                    turnWasStarted = true;
                } else {
                    tickBuffer = 10;
                    turnMove();
                }
            }
        } else if (!opponentTurn && !isTurn && tickBuffer == 0) {
            opponentTurn = true;
            opponent();
        }
        if (tickBuffer > 0) {
            tickBuffer--;
        }
        return !isFinish();
    }

//    public void initLosingFields() {
//        boolean[] used = new boolean[30];
//        for (int i = 0; i < 30; i++) {
//            used[i] = false;
//        }
//        int tempNextValue = 1;
//        for (int i = 0; i < (losingFields.length / 2); i++) {
//            for (int j = 0; j < 30; j++) {
//                if (!used[j]) {
//                    tempNextValue = j;
//                }
//            }
//            losingFields[i][1] = tempNextValue;
//            used[losingFields[i][1]] = false;
//            losingFields[i][2] = losingFields[i][1] + i;
//            used[losingFields[i][2]] = false;
//
//        }
//    }
//
//    public boolean isInLosingPosition(int x, int y) {
//        for (int i = 0; i < (losingFields.length / 2); i++) {
//            if (losingFields[i][1] == x || losingFields[i][2] == x) {
//                if (losingFields[i][1] == x && losingFields[i][2] == y) {
//                    return true;
//                }
//                if (losingFields[i][2] == x && losingFields[i][1] == y) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

    public void opponent() {
        //move
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;
        int randomNum2 = rand.nextInt(5) + 1;
        if (randomNum == 1 && queen.getCenterX() > (randomNum2 + 1) * groesseFelder) {
            queen.move(-(randomNum2 + 1) * groesseFelder, 0);
        } else if (randomNum == 2 && (randomNum2 + 1) * groesseFelder + queen.getCenterY() < 30 * groesseFelder) {
            queen.move(0, (randomNum2 + 1) * groesseFelder);
        } else if (queen.getCenterX() > (randomNum2 + 1) * groesseFelder && (randomNum2 + 1) * groesseFelder + queen.getCenterY() < 30 * groesseFelder) {
            queen.move(-(randomNum2 + 1) * groesseFelder, 0);
            queen.move(0, (randomNum2 + 1) * groesseFelder);
        } else {
            if (queen.getCenterX() > groesseFelder) {
                queen.move(-groesseFelder, 0);
            } else if (queen.getCenterY() < groesseFelder + 28 * groesseFelder) {
                queen.move(0, groesseFelder);
            }
        }
        isTurn = true;
        opponentTurn = false;
    }

    public void moveToLeft() {
        if (queen.getCenterX() - queen.getShapeWidth() / 2 - groesseFelder >= 0) {
            queen.moveTo(queen.getCenterX() - queen.getShapeWidth() / 2 - groesseFelder, queen.getCenterY() - queen.getShapeHeight() / 2);
        }
    }

    public void moveDown() {
        if (queen.getCenterY() - queen.getShapeHeight() / 2 + groesseFelder <= view.getHeight() / 2d + 14 * groesseFelder) {
            queen.moveTo(queen.getCenterX() - queen.getShapeWidth() / 2, queen.getCenterY() - queen.getShapeHeight() / 2 + groesseFelder);
        }

    }

    public void moveDiagonally() {
        if ((queen.getCenterY() - queen.getShapeHeight() / 2 + groesseFelder <= view.getHeight() / 2d + 14 * groesseFelder) && queen.getCenterX() - queen.getShapeWidth() / 2 - groesseFelder >= 0) {
            queen.moveTo(queen.getCenterX() - queen.getShapeWidth() / 2 - groesseFelder, queen.getCenterY() - queen.getShapeHeight() / 2 + groesseFelder);
        }

    }

    public boolean isFinish() {
        boolean contains = zeroField.contains(queen);
        if (contains) {
            boolean wonB = !isTurn;
            GameOverLay overlay = new GameOverLay(view, controller);
            overlay.setGameData(this.getClass(), wonB);
            ViewContents.getInstance().runViewContent(overlay);
        }
        return contains;
    }

    public void turn() {
        markerTurn.moveTo(queen.getCenterX() - queen.getShapeWidth() / 4, queen.getCenterY() - queen.getShapeHeight() / 4);
        markerTurn.setHidden(false);
        turnMove();
    }

    public void moveToLeftMarker() {
        if (markerTurn.getCenterX() - markerTurn.getShapeWidth() / 2 - groesseFelder >= 0) {
            markerTurn.moveTo(markerTurn.getCenterX() - markerTurn.getShapeWidth() / 2 - groesseFelder, markerTurn.getCenterY() - markerTurn.getShapeHeight() / 2);
        }
    }

    public void moveDownMarker() {
        if (markerTurn.getCenterY() - markerTurn.getShapeHeight() / 2 + groesseFelder <= (view.getHeight() / 2d + 14 * groesseFelder + markerTurn.getShapeHeight() / 2)) {
            markerTurn.moveTo(markerTurn.getCenterX() - markerTurn.getShapeWidth() / 2, markerTurn.getCenterY() - markerTurn.getShapeHeight() / 2 + groesseFelder);
        }

    }

    public void moveDiagonallyMarker() {
        if (markerTurn.getCenterY() - markerTurn.getShapeHeight() / 2 + groesseFelder <= (view.getHeight() / 2d + 14 * groesseFelder + markerTurn.getShapeHeight() / 2) && markerTurn.getCenterX() - markerTurn.getShapeWidth() / 2 - groesseFelder >= 0) {
            markerTurn.moveTo(markerTurn.getCenterX() - markerTurn.getShapeWidth() / 2 - groesseFelder, markerTurn.getCenterY() - markerTurn.getShapeHeight() / 2 + groesseFelder);
        }

    }

    public void turnMove() {
        if (!view.keyEnterPressed()) {
            turnMoveOnce();
        } else if(!queen.contains(markerTurn)){
            isTurn = false;
            turnWasStarted = false;
            markerTurn.setHidden(true);
            int[] whereIsMarker = new int[2];
            whereIsMarker = whereIsInt(markerTurn);
            if (whereIsMarker != null) {
                queen.moveTo(fields[whereIsMarker[0]][whereIsMarker[1]].getShapeX(), fields[whereIsMarker[0]][whereIsMarker[1]].getShapeY());
            }
            tickBuffer = 20;
        }
    }

    public void turnMoveOnce() {
        if (view.keyPressed('a') || view.keyLeftPressed()) {
            if (Math.round(markerTurn.getCenterY()) == Math.round(queen.getCenterY())) {
                if (markerTurn.getCenterX() > groesseFelder) {
                    markerTurn.move(-groesseFelder, 0);
                }
            } else if (Math.round(markerTurn.getCenterX()) == Math.round(queen.getCenterX())) {
                int[] markerLocation = whereIsInt(markerTurn);
                int[] queenLocation = whereIsInt(queen);
                if (markerLocation != null && queenLocation != null) {
                    int differenceYMarkerminusQueen = markerLocation[1] - queenLocation[1];
                    if(markerTurn.getCenterX()>groesseFelder*differenceYMarkerminusQueen){
                        markerTurn.move(-groesseFelder * differenceYMarkerminusQueen, 0);
                    }
                }
            }
        }
        if (view.keyPressed('s') || view.keyDownPressed()) {
            if (Math.round(markerTurn.getCenterX()) == Math.round(queen.getCenterX())) {
                if (markerTurn.getCenterY() < 29 * groesseFelder) {
                    markerTurn.move(0, groesseFelder);
                }
            } else if (Math.round(markerTurn.getCenterY()) == Math.round(queen.getCenterY())) {
                int[] markerLocation = whereIsInt(markerTurn);
                int[] queenLocation = whereIsInt(queen);
                if (markerLocation != null && queenLocation != null) {
                    int differenceYMarkerminusQueen = queenLocation[0] - markerLocation[0];
                    if(markerTurn.getCenterY()<29*differenceYMarkerminusQueen-groesseFelder*differenceYMarkerminusQueen){
                        markerTurn.move(0, -groesseFelder * differenceYMarkerminusQueen);
                    }
                }
            }
        }
    }

    public int[] whereIsInt(Circle c) {
        for (int i = 0; i < 30; i++) { //y
            for (int j = 0; j < 30; j++) { //x
                if (fields[j][i].contains(c)) {
                    int[] returnInt = new int[2];
                    returnInt[0] = j;
                    returnInt[1] = i;
                    return returnInt;
                }
            }
        }
        return null;
    }

}
