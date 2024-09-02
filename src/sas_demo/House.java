package sas_demo;

import sas.*;

import java.awt.Color;


public class House extends Sprite {

    private View view;
    private Rectangle house;
    private Rectangle window1;
    private Rectangle window2;
    private Rectangle door;
    private Circle doorbell;
    private Polygon roof;

    public House(View pView) {

        view = pView;

        house = new Rectangle(300, 100, 300, 180, Color.GRAY);
        window1 = new Rectangle(350, 150, 40, 40, Color.YELLOW);
        window2 = new Rectangle(500, 150, 40, 40, Color.YELLOW);
        doorbell = new Circle(415, 240, 5.0, Color.RED);
        door = new Rectangle(430, 210, 40, 70, new Color(122, 78, 0));

        roof = new Polygon(300, 100, Color.BLACK);
        roof.add(300, 0);
        roof.add(250, -50);
        roof.add(50, -50);

        add(house);
        add(window1);
        add(window2);
        add(doorbell);
        add(door);
        add(roof);

    }

    public void blink() {

        for (int i = 0; i < 6; i++) {
            if (i % 2 == 0) {
                window1.setColor(Color.PINK);
                window2.setColor(Color.PINK);
            } else {
                window1.setColor(Color.YELLOW);
                window2.setColor(Color.YELLOW);
            }
            view.wait(1000);
        }
    }

    public Circle getDoorbell() {
        return doorbell;
    }

}