package viewcontents.DonkeyKong;
import sas.*;
public class DonkeyKong {
    private Picture dk;
    public DonkeyKong(int posX, int posY){
        dk = new Picture(posX,posY, 50,50, "/resources/mario.png");
    }
}
