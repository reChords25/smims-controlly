package viewcontents.tank;

import viewcontents.AbstractViewContent;

public class Trophy extends ScalablePicture {

    private static final String PATH_IMAGE = AbstractViewContent.PATH_TO_RESOURCES + "frogger/goblet.png";

    public Trophy(int x, int y) {
        super(x, y, PATH_IMAGE);
    }

}
