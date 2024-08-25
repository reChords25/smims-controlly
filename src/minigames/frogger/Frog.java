package minigames.frogger;

import minigames.AbstractGame;
import sas.Picture;

public class Frog extends ScalablePicture {

    /* Static Variables */
    private static final String PATH_IMAGE = "frogger/frog.png";

    /* Static Methods */

    /* Object Variables */
    
    /* Constructors */
    protected Frog(int x, int y) {
        super(x, y, AbstractGame.PATH_TO_RESOURCES + PATH_IMAGE);
    }
    
    /* Object Methods */
    
    /* Getters and Setters */
    
    /* Inner Classes */
    
}
