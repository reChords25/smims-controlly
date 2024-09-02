package minigames.frogger.obstacles;

import minigames.frogger.ScalablePicture;

public abstract class Obstacle extends ScalablePicture {

    public Obstacle(double xp, double yp, String image) {
        super(xp, yp, image);
    }

}
