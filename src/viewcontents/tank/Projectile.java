package viewcontents.tank;

import util.ScalablePicture;
import viewcontents.AbstractViewContent;

public class Projectile extends ScalablePicture {

    private static final String PATH_IMAGE = AbstractViewContent.PATH_TO_RESOURCES + "CactiCatch/shooting.png";

    public Projectile(int x, int y) {
        super(x, y, PATH_IMAGE);
    }

}