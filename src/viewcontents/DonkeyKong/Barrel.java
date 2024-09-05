package viewcontents.DonkeyKong;

import java.awt.*;
import sas.*;
import sas.Rectangle;
public class Barrel {

    private Circle kreis;
    private Rectangle kreuz1;
    private Rectangle kreuz2;
    private Sprite barrel;
    boolean directionRight;
    boolean falling;

    public Barrel(int posX, int posY){
        int radius = 30;
        directionRight = true;
        falling = false;
        kreis = new Circle(posX, posY,radius, new Color(102,51,0));
        kreuz1 = new Rectangle(posX+20,posY+10,radius-10,radius+10,Color.LIGHT_GRAY);
        kreuz2 = new Rectangle(posX+10,posY+20,radius+10,radius-10,Color.LIGHT_GRAY);
        barrel = new Sprite(kreis);
        barrel.add(kreuz1);
        barrel.add(kreuz2);
    }

    public Sprite getBarrel() {
        return barrel;
    }

    public void moveBarrel(){
        if(!falling) {
            if (directionRight) {
                barrel.moveTo(barrel.getShapeX() + 5, barrel.getShapeY());
            }
            if (!directionRight) {
                barrel.moveTo(barrel.getShapeX() - 5, barrel.getShapeY());
            }
        }
    }

    public void turnBarrel(){
        if (directionRight) {
            barrel.turn(5);
        }
        if (!directionRight){
            barrel.turn(-5);
        }
    }

    public void fallBarrel() {
        if (falling) {
            barrel.moveTo(barrel.getShapeX(), barrel.getShapeY() + 5);
        }
    }

    public boolean getDirectionRight() {
        return directionRight;
    }

    public void setDirectionRight( boolean b){
        directionRight = b;
    }

    public boolean getFalling(){
        return falling;
    }

    public void setFalling(boolean pfalling) {
        falling = pfalling;
    }

}
