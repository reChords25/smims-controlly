package viewcontents.tank;

import viewcontents.AbstractViewContent;

public class Cannon extends ScalablePicture {

    private static final String PATH_IMAGE = AbstractViewContent.PATH_TO_RESOURCES + "CactiCatch/wand.png";

    public Cannon(int x, int y) {
        super(x, y, PATH_IMAGE);
    }

}