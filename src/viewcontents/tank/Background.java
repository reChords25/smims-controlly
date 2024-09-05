package viewcontents.tank;

import viewcontents.AbstractViewContent;

public class Background extends ScalablePicture {

    private static final String PATH_IMAGE = AbstractViewContent.PATH_TO_RESOURCES + "CactiCatch/mapMid.png";

    public Background(int x, int y) {
        super(x, y, PATH_IMAGE);
    }

}