package viewcontents.tank;

import viewcontents.AbstractViewContent;

public class Trophy extends ScalablePicture {

    private int lastDamage = 0;
    private int damage = 1;
    private int hp = 3;
    private int speed = 3;

    public Trophy(int x, int y, String PATH_IMAGE) {
        super(x, y, PATH_IMAGE);
    }

    public int getLastDamage(){
        return lastDamage;
    }

    public int getDamage(){
        return damage;
    }

    public int getHp(){
        return hp;
    }

    public int getSpeed(){
        return hp;
    }

    public void setSpeed(int pSpeed){
        hp = pSpeed;
    }

    public void setHp(int pHp){
        hp = pHp;
    }


    public void setDamage(int pDamage){
        damage = pDamage;
    }

    public void setLastDamage(int pLastDamage){
        lastDamage = pLastDamage;
    }

}
