package viewcontents.tank;

import viewcontents.AbstractViewContent;

public class Heart extends ScalablePicture {

    private static final String PATH_IMAGE = AbstractViewContent.PATH_TO_RESOURCES + "CactiCatch/herz.png";

    public Heart(int x, int y) {
        super(x, y, PATH_IMAGE);
    }

}