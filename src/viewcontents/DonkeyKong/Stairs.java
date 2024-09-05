package viewcontents.DonkeyKong;
import sas.*;
import viewcontents.AbstractViewContent;

public class Stairs {
    private static final String PATH_TO_DonkeyKong = AbstractViewContent.PATH_TO_RESOURCES + "DonkeyKong/";
    private Picture stair;

    public Stairs(int posX, int posY) {
        stair = new Picture(posX,posY, 40,120, PATH_TO_DonkeyKong + "stair_pink.png");
    }

    public Picture getStair() {
        return stair;
    }

}
