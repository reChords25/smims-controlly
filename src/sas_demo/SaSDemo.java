package sas_demo;

import sas.Circle;
import sas.Picture;
import sas.View;

import java.awt.*;

public class SaSDemo {

    private View view;

    public SaSDemo() {
        view = new View(800, 600);
    }

    public void demo1() {
        view.setName("Demo: Ball");
        Circle ball = new Circle(100, 100, 20, Color.BLACK);
        while (ball.getCenterX() < view.getWidth()) {
            if (view.keyRightPressed()) {
                ball.move(20);
            }
            view.wait(20);
        }
    }

    public void demo2() {

        view.setName("Demo: Winter Wonderland");

        Snowman snowman = new Snowman(view, Color.PINK);
        snowman.moveTo(100, 100);
        House house = new House(view);

        Picture santa = new Picture(20, 500, "resources/snowman.png");
        double stepLength = 10;

        while (!santa.intersects(house.getDoorbell())) {

            if (view.keyRightPressed()) {
                santa.move(stepLength);
            }

            if (view.keyLeftPressed()) {
                santa.move(-stepLength);
            }

            if (view.keyUpPressed()) {
                santa.move(0, -stepLength);
            }

            if (view.keyDownPressed()) {
                santa.move(0, stepLength);
            }

            view.wait(20);
        }

        house.blink();
        snowman.dance();
    }
}
