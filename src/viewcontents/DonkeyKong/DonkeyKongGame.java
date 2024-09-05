package viewcontents.DonkeyKong;

import controller.AbstractController;
import sas.Picture;
import sas.Rectangle;
import sas.Tools;
import sas.View;
import util.Button;
import util.GameOverLay;
import viewcontents.AbstractViewContent;
import viewcontents.ViewContents;

import java.awt.*;
import java.util.ArrayList;

public class DonkeyKongGame extends AbstractViewContent {

    Button but;
    private static final String PATH_TO_DonkeyKong = AbstractViewContent.PATH_TO_RESOURCES + "DonkeyKong/";
    private Rectangle[] floors;
    private Rectangle groundfloor;
    private Stairs[] stairs;
    private Rectangle stair;
    private Mario mario;
    private Rectangle rRight;
    private Rectangle rLeft;
    private ArrayList<Barrel> barrels;
    private Rectangle deleteZone;
    private Picture dk;

    public DonkeyKongGame(View view, AbstractController controller) {
        super(view, controller);
    }

    @Override
    protected void initView() {
        Rectangle background = new Rectangle(0,0,900,700);
        shapesToRemove.add(background);
        but = new Button(0, 0, 300, 50, "hallo", Color.RED);
        buttonsToRemove.add(but);
        floors = new Rectangle[5];
        stairs= new Stairs[5];
        rLeft = new Rectangle(0,0,100,700);
        shapesToRemove.add(rLeft);
        rRight = new Rectangle(800,0, 100,700);
        shapesToRemove.add(rRight);
        groundfloor = new Rectangle(0,650,900,50, Color.green);
        shapesToRemove.add(groundfloor);
        for(int i=1; i<floors.length+1; i++) {
            if(i % 2 == 0) {
                floors[i-1] = new Rectangle(100, i * 120+50, 800, 50, Color.green);
                shapesToRemove.add(floors[i-1]);
                stairs[i-1] = new Stairs(Tools.randomNumber(100, 650),i*120+50);
                shapesToRemove.add(stairs[i-1].getStair());
            }
            else {
                floors[i-1] = new Rectangle(0, i*120+50, 800,50, Color.green);
                shapesToRemove.add(floors[i-1]);
                stairs[i-1] = new Stairs(Tools.randomNumber(100, 650),i*120+50);
                shapesToRemove.add(stairs[i-1].getStair());
            }
        }
        barrels = new ArrayList<Barrel>();
        barrels.add(new Barrel(100,110));

        for(int i=0; i<barrels.size();i++){
            shapesToRemove.add(barrels.get(i).getBarrel());
        }
        dk = new Picture(10,120,50,50,PATH_TO_DonkeyKong +"donkey_kong.png");
        shapesToRemove.add(dk);
        mario = new Mario(500, 600, stairs, barrels);
        shapesToRemove.add(mario.getMario());

        deleteZone = new Rectangle(700,580,100,70);
        shapesToRemove.add(deleteZone);

    }

    public void deleteBarrels(){
        for(int i=0; i<barrels.size();i++){
            if(deleteZone.contains(barrels.get(i).getBarrel())){
                barrels.get(i).getBarrel().setHidden(true);
                view.remove(barrels.get(i).getBarrel());
            }
        }
    }

    public void spawnBarrel() {
        int ranInt = Tools.randomNumber(1, 50);
        if (ranInt==2) {
            barrels.add(new Barrel(100,110));
            for(int i=0; i<barrels.size();i++){
                shapesToRemove.add(barrels.get(i).getBarrel());
            }
        }
    }

    public void changeDirectionBarrels() {
        for (int i=0; i<barrels.size(); i++) {
            if (checkKollisionBarrelFloorGetIndex(barrels.get(i))!=99) {
                if(checkKollisionBarrelFloorGetIndex(barrels.get(i))%2==0) {
                    barrels.get(i).setDirectionRight(true);
                }
                else barrels.get(i).setDirectionRight(false);
            }
        }
    }

    public void barrelFallDown() {
        for (int i=0; i<barrels.size(); i++){
            if(rLeft.contains(barrels.get(i).getBarrel()) && !checkKollisionBarrelFloor(barrels.get(i))) {
                barrels.get(i).setFalling(true);
            }
            else if(rRight.contains(barrels.get(i).getBarrel()) && !checkKollisionBarrelFloor(barrels.get(i))) {
                barrels.get(i).setFalling(true);
            }
            else{
                barrels.get(i).setFalling(false);
            }
        }
    }

    public boolean checkKollisionBarrelFloor(Barrel pBarrel) {
        boolean b = false;
            for (int j = 0; j < floors.length; j++) {
                if (pBarrel.getBarrel().intersects(floors[j])) {
                    b = true;
                }
            }
        return b;
    }

    public int checkKollisionBarrelFloorGetIndex(Barrel pBarrel) {
        int z = 0;
        if(checkKollisionBarrelFloor(pBarrel)) {
            while (!pBarrel.getBarrel().intersects(floors[z])) {
                z++;
            }
            return z;
        }
        else return 99;
    }


    @Override
    public boolean tick() {
        if (but.clicked()) {
            ViewContents.getInstance().runViewContent(new GameOverLay(view, controller));
            return false;
        }

        for (int i=0; i<barrels.size(); i++){
            barrels.get(i).turnBarrel();
        }

        if(view.keyLeftPressed()) {
            mario.moveLeft();
        }
        if(view.keyRightPressed()) {
            mario.moveRight();
        }
        if(view.keyUpPressed()) {
            mario.moveUp();
        }
        if(view.keyDownPressed()) {
            mario.moveDown();
        }
        deleteBarrels();
        barrelFallDown();
        changeDirectionBarrels();
        spawnBarrel();
        if(mario.checkKollisionBarrels()){
            mario.getMario().setHidden(true);
        }
        for (int i = 0; i < barrels.size(); i++) {
            barrels.get(i).fallBarrel();
            barrels.get(i).moveBarrel();
        }
        if (mario.getMario().intersects(dk)) {
            dk.setHidden(true);
        }

        return true;

    }
}

