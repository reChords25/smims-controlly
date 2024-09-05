package viewcontents.DonkeyKong;
import sas.*;
import viewcontents.AbstractViewContent;

public class Mario {

    private static final String PATH_TO_DonkeyKong = AbstractViewContent.PATH_TO_RESOURCES + "DonkeyKong/";
    private Picture mario;
    private boolean orientationRight = true;
    private Stairs[] stairs;

    public Mario(int posX, int posY, Stairs[] pStairs){
        mario = new Picture(posX,posY, 50,50, PATH_TO_DonkeyKong + "mario.png");
        stairs = pStairs;
    }

    public void moveLeft(){
            if (orientationRight) {
                mario.flipHorizontal();
                orientationRight = false;
            }
            mario.move(-5);
    }

    public void moveRight(){
        if (!orientationRight) {
            mario.flipHorizontal();
            orientationRight = true;
        }
        mario.move(5);
    }

    public boolean checkKollisionStairs(){
        boolean b = false;
        for(int i=0; i<4; i++){
            if (mario.intersects(stairs[i].getStair())) {
                b = true;
            }
        }
        return b;
    }

    public void moveUp() {
        if (checkKollisionStairs()) {
            mario.moveTo(mario.getShapeX(), mario.getShapeY() - 5);
        }
    }

    public void moveDown() {
        if (checkKollisionStairs()) {
            mario.moveTo(mario.getShapeX(), mario.getShapeY() + 5);
        }
    }

    public Picture getMario() {
        return mario;
    }
}
