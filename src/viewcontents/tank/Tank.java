package viewcontents.tank;


import viewcontents.AbstractViewContent;

public class Tank extends ScalablePicture {

    private static final String PATH_IMAGE = AbstractViewContent.PATH_TO_RESOURCES + "frogger/frog.png";

    protected Tank(int x, int y) {
        super(x, y, PATH_IMAGE);
    }

}
