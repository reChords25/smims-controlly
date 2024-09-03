package viewcontents.tank;

import controller.AbstractController;
//import minigames.frogger.obstacles.Auto;
//import minigames.frogger.obstacles.Obstacle;
import sas.Sprite;
import sas.Text;
import sas.Tools;
import sas.View;
import viewcontents.AbstractViewContent;

import java.lang.Math;

import java.util.ArrayList;


public class TankGame extends AbstractViewContent {

    /* Static Variables */
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private static final int SLEEP_TIME = 20;
    private static final int SPEED_OBSTACLE = 15;
    private static final double SPEED_Tank = 10;
    private static final double OBJECT_HEIGHT = 50;
    private static boolean gewonnen = false;
    private static int winningNumber = 0;


    private Sprite ptank;
    private Tank tank;
    private Cannon cannon;
    private Projectile projectile;
    private ArrayList<Projectile> bullets;
    private ArrayList<Trophy> hindernisse;


    private int spawnChance;

    /* Constructors */
    public TankGame(View view, AbstractController controller) {
        super(view, controller);

        this.tank = null;
        this.bullets = new ArrayList<>();
        this.hindernisse = new ArrayList<>();
        this.spawnChance = 10;
    }

    /* Object Methods */
    @Override
    protected void initView() {

        view.setName("SMIMS TankGame");

    }

    boolean hasRunOnce = false;
    @Override
    public boolean tick() {

        runGame();
        return false;
    }

    public void runGame() {
        if(!hasRunOnce){
            hasRunOnce = true;
            gewonnen = false;

            // Spawns the Tank


            tank = new Tank(200, 200);
            tank.scaleTo(50, 50);

            //ptank = new Sprite(tank);
            cannon = new Cannon(200, 200);
            cannon.scaleTo(50, 50);

            //ptank.add(cannon);
            //ptank.scaleTo(50, 50);

            spawneHindernisse();
        }


        while (!gewonnen) {
            moveTank();
            shoot();
            rotateCannon();
            projectileTouchesTarget();
            moveProjectiles();

            view.wait(SLEEP_TIME); // Das hier ist sehr wichtig, weil euer Spiel sonst zu schnell läuft.
            if (winningNumber >= spawnChance) {
                gewonnen = true;
            }

        }

        new Text(view.getWidth() / 2 - 30, view.getHeight() / 2 - 10, "Gewonnen!");
    }


    private void spawneHindernisse() {
        for (int i = 0; i < spawnChance; i++) {
            Trophy hindernis = new Trophy(Tools.randomNumber(1, view.getHeight()), Tools.randomNumber(1, view.getWidth()));
            hindernis.scaleTo(OBJECT_HEIGHT);
            hindernisse.add(hindernis);
        }
    }

    double currentRotation = 0;
    double cannonRotation = 0;
    double xPosition = 200;
    double yPosition = 200;


    private void moveTank() {
        double x = controller.getLJoystickX() * SPEED_Tank;
        double y = controller.getLJoystickY() * SPEED_Tank;
        System.out.println(x);
        System.out.println(y);
        tank.move(-y);
        xPosition = tank.getCenterX();
        yPosition = tank.getCenterY();
        System.out.println(tank.getCenterX());
        cannon.moveTo(xPosition - 25, yPosition - 25);
        double finalDegree = 0;
        if (y > 0) {
            System.out.println("asdf");
            finalDegree = -x;
        } else {
            finalDegree = x;
        }

        tank.turn(finalDegree);
        cannon.turn(finalDegree);
        currentRotation += finalDegree;
        cannonRotation += finalDegree;

    }

    private void rotateCannon() {
        double x = controller.getRJoystickX() * SPEED_Tank;
        double y = controller.getRJoystickY() * SPEED_Tank;
        if (x != 0 || y != 0) {
            double rotationNeeded = (getCannonDegrees(x, y) - cannonRotation);

            if (rotationNeeded > 180) {
                rotationNeeded = rotationNeeded - 360;
            } else if (rotationNeeded < -180) {
                rotationNeeded = rotationNeeded + 360;
            }
//            if(rotationNeeded>=10){
//                cannon.turn(10);
//            }
//            else{
                cannon.turn(rotationNeeded);
//            }

            updateRotation(rotationNeeded);
        }
    }

    private void updateRotation(double newRot) {
        cannonRotation += newRot;
        while ((cannonRotation - 360) >= 0) {
            cannonRotation = cannonRotation - 360;
        }
    }

    private void shoot() {
        if (controller.getBtn1Pressed()) {
            projectile = new Projectile((int) xPosition, (int) yPosition);
            projectile.scaleTo(10);
            projectile.moveTo(xPosition, yPosition);

            projectile.turn(cannonRotation);
            bullets.add(projectile);
        }

    }

    private void moveProjectiles() {

        ArrayList<Projectile> deletableProjectiles = new ArrayList<>();

        for (Projectile bullet : bullets) {
            bullet.move(SPEED_OBSTACLE);

            // Haben wir den Rand erreicht? Dann können wir das Hindernis aus dem Spiel entfernen.
            if (bullet.getShapeX() > view.getWidth()) {
                deletableProjectiles.add(bullet);
            }
        }

        // Entferne alle Hindernisse, die nicht mehr zu sehen sind.
        for (Projectile bullet : deletableProjectiles) {
            bullet.setHidden(true);
            view.remove(bullet);
            bullets.remove(deletableProjectiles);
        }
    }


    private boolean projectileTouchesTarget() {
        for (Trophy target : hindernisse) {
            for (Projectile bullet : bullets) {
                if (bullet.intersects(target)) {
                    view.remove(target);
                    hindernisse.remove(target);
                    view.remove(bullet);
                    bullets.remove(bullet);
                    winningNumber = winningNumber + 1;
                    return true;
                }
            }
        }
        return false;
    }

    private double getDegrees(double x, double y) {
        if (x == 0) {
            if (y > 0) {
                return -90;
            } else {
                return -90;
            }
        } else if (y == 0 && x < 0) {

            return -180;

        } else if (x > 0) {

            if (y > 0) {
                System.out.println("PP");
                return -180 + Math.toDegrees(Math.atan(divide(x, y)));
            } else {
                System.out.println("PN");
                return Math.toDegrees(Math.atan(divide(x, y)));
            }
        } else {
            if (y > 0) {
                System.out.println("NP");
                return Math.toDegrees(Math.atan(divide(x, y)));
            } else {
                return -180 + Math.toDegrees(Math.atan(divide(x, y)));
            }
        }
    }

    private double getCannonDegrees(double x, double y) {
        if (x == 0) {
            if (y > 0) {
                return 90;
            } else {
                return -90;
            }
        } else if (y == 0 && x < 0) {

            return -180;

        } else if (x > 0) {

            if (y > 0) {
                System.out.println("PP");
                return Math.toDegrees(Math.atan(divide(x, y)));
            } else {
                System.out.println("PN");
                return Math.toDegrees(Math.atan(divide(x, y)));
            }
        } else {
            if (y > 0) {
                System.out.println("NP");
                return -180 + Math.toDegrees(Math.atan(divide(x, y)));
            } else {
                return -180 + Math.toDegrees(Math.atan(divide(x, y)));
            }
        }
    }

    double divide(double dividend, double divisor) {
        if (divisor == 0.0) {
            return 0; // or handle error appropriately
        }

        double quotient = 0.0;
        double sign = 1.0;

        // Handle negative numbers
        if (dividend < 0.0) {
            dividend = -dividend;
            sign = -sign;
        }
        if (divisor < 0.0) {
            divisor = -divisor;
            sign = -sign;
        }

        // Subtract divisor from dividend while keeping track of the quotient
        while (dividend >= divisor) {
            dividend -= divisor;
            quotient += 1.0;
        }

        // Handle the remainder to make the division more accurate
        double fractional_part = 0.0;
        double remainder = dividend;
        double precision = 0.1; // Adjust this value for higher precision

        // Approximate the fractional part
        while (remainder >= precision) {
            remainder -= divisor * precision;
            fractional_part += precision;
        }

        return sign * (quotient + fractional_part);
    }

    /* Getters and Setters */

    /* Inner Classes */

}

//    private void bewegeHindernisse() {
//
//        ArrayList<Obstacle> zuEntfernendeHindernisse = new ArrayList<>();
//
//        for (Obstacle hindernis : hindernisse) {
//            hindernis.move(SPEED_OBSTACLE);
//
//            // Haben wir den Rand erreicht? Dann können wir das Hindernis aus dem Spiel entfernen.
//            if (hindernis.getShapeX() > view.getWidth()) {
//                zuEntfernendeHindernisse.add(hindernis);
//            }
//        }

// Entferne alle Hindernisse, die nicht mehr zu sehen sind.
//            hindernis.setHidden(true);
//            view.remove(hindernis);
//            hindernisse.remove(zuEntfernendeHindernisse);
//        }
//    }

// Replace with the actual method to get the rotation
//        if(x != 0 && y != 0) {
//       // Calculate the rotation needed to achieve exactly 90 degrees
//        System.out.println(Math.toDegrees(Math.atan(divide(x, y))));
//double desiredRotation = -Math.toDegrees(Math.atan(divide(x, y)));
//double rotationNeeded = desiredRotation - currentRotation;

//           leopard.turn(rotationNeeded);
//currentRotation += rotationNeeded;
//       }
