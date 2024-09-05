package viewcontents.tank;

import sas.Picture;

import java.awt.image.BufferedImage;

public class ScalablePicture extends Picture {

    public ScalablePicture(double xp, double yp, String name) {
        super(xp, yp, name);
    }

    public ScalablePicture(double xp, double yp, double w, double h, String name) {
        super(xp, yp, w, h, name);
    }

    protected ScalablePicture(double xp, double yp, BufferedImage image) {
        super(xp, yp, image);
    }

    public void scaleTo(double height) {
        scaleTo((height * getWidth()) / getHeight(), height);
    }

}