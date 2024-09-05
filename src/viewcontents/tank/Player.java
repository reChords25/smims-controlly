package viewcontents.tank;


import util.ScalablePicture;
import viewcontents.AbstractViewContent;

public class Player extends ScalablePicture {

    private static final String PATH_IMAGE = AbstractViewContent.PATH_TO_RESOURCES + "CactiCatch/avatar.png";

    protected Player(int x, int y) {
        super(x, y, PATH_IMAGE);
    }

}
