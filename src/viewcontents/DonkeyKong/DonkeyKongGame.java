package viewcontents.DonkeyKong;

import controller.AbstractController;
import sas.Circle;
import sas.Rectangle;
import sas.View;
import util.Button;
import util.GameOverLay;
import viewcontents.AbstractViewContent;
import viewcontents.ViewContents;

import java.awt.*;

public class DonkeyKongGame extends AbstractViewContent {

    Button but;
    private Rectangle floor1;
    private Rectangle floor2;
    private Rectangle stair;
    private DonkeyKong dk;


    public DonkeyKongGame(View view, AbstractController controller) {
        super(view, controller);
    }

    @Override
    protected void initView() {
        but = new Button(0, 0, 300, 50, "hallo", Color.RED);
        buttonsToRemove.add(but);
        floor1 = new Rectangle(0,850,900,50, Color.green);
        floor2 = new Rectangle(100, 750, 800,50 , Color.green);
        stair = new Rectangle(400, 760, 40,80, Color.blue);
        dk = new DonkeyKong(500, 850);
        }



    @Override
    public boolean tick() {
        if (but.clicked()) {
            ViewContents.getInstance().runViewContent(new GameOverLay(view, controller, this.getClass(), false));
            return false;
        }
        return true;
    }
}

