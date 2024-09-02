package sas_demo;

import sas.*;

import java.awt.Color;

public class Snowman extends Sprite {

    private View view;
    private Color color;
    private Circle head;
    private Circle belly;
    private Circle legs;
    private Rectangle hat;

    public Snowman(View pView, Color pFarbe) {
        view = pView;
        color = pFarbe;
        head = new Circle(10.0, 20.0, 20.0, color);
        belly = new Circle(5.0, 50.0, 25.0, color);
        legs = new Circle(0.0, 85.0, 30.0, color);
        hat = new Rectangle(5.0, 0.0, 50.0, 25, Color.BLACK);

        add(head);
        add(belly);
        add(legs);
        add(hat);
    }

    public void changeColor(Color pColor) {
        color = pColor;
        head.setColor(color);
        belly.setColor(color);
        legs.setColor(color);
    }

    public void dance() {
        belly.move(10.0);
        legs.move(-10.0);
        for (int i = 0; i < 4; i++) {
            view.wait(500);
            belly.move(-20.0);
            legs.move(20.0);
            view.wait(500);
            belly.move(20.0);
            legs.move(-20.0);
        }
        view.wait(500);
        belly.move(-10.0);
        legs.move(10.0);

    }

    public Color getColor() {
        return color;
    }
}
