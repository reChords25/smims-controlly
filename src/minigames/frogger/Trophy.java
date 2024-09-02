package minigames.frogger;

import minigames.AbstractGame;

public class Trophy extends ScalablePicture {

    private static final String PATH_IMAGE = AbstractGame.PATH_TO_RESOURCES + "frogger/goblet.png";

    public Trophy(int x, int y) {
        super(x, y, PATH_IMAGE);
    }

}
