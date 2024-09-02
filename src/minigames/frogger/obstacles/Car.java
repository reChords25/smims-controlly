package minigames.frogger.obstacles;

import minigames.AbstractGame;
import sas.Tools;

public class Car extends Obstacle {

    private static final String PATH_IMAGE = AbstractGame.PATH_TO_RESOURCES + "frogger/car";

    public Car(double xp, double yp) {
        super(xp, yp, PATH_IMAGE + Tools.randomNumber(1, 12) + ".png");
    }

}
