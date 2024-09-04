package viewcontents.tank;

import controller.AbstractController;
import sas.Sprite;
import sas.Text;
import sas.Tools;
import sas.View;
import viewcontents.AbstractViewContent;

import java.lang.Math;

import java.util.ArrayList;


public class TankGame extends AbstractViewContent {

    private static final String PATH_IMAGE = AbstractViewContent.PATH_TO_RESOURCES + "frogger/car1.png";
    private static final String PATH_IMAGEB = AbstractViewContent.PATH_TO_RESOURCES + "CactiCatch/border.png";

    /* Static Variables */
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private static final int SLEEP_TIME = 20;
    private static final double SPEED_OBSTACLE = 2.5;
    private static final int SPEED_Projectile = 25;
    private static final double SPEED_Tank = 3;
    private static final double OBJECT_HEIGHT = 50;
    private static boolean gewonnen = false;
    private static int winningNumber = 0;
    public int tankHP;

    private Sprite ptank;
    private Tank tank;
    private Cannon cannon;
    private Projectile projectile;
    private ArrayList<Projectile> bullets;
    private ArrayList<Trophy> hindernisse;
    private ArrayList<BasicObstacle> obstacles;


    private int spawnChance;
    private int score;

    /* Constructors */
    public TankGame(View view, AbstractController controller) {
        super(view, controller);

        this.tank = null;
        this.bullets = new ArrayList<>();
        this.hindernisse = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.spawnChance = 43;
        this.tankHP = 3;
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

            Background background = new Background(0, 0);
            //System.out.println(view.getWidth());
            background.scaleTo(view.getWidth(), view.getHeight());
            background.moveTo(0,0);

            // Spawns the Tank


            tank = new Tank(200, 200);
            tank.scaleTo(37, 55);
            tank.moveTo(200, 200);

            //ptank = new Sprite(tank);
            cannon = new Cannon(200, 200);
            cannon.scaleTo(20, 20);
            cannon.moveTo(232, 222);
            Heart heart = new Heart(1, 1);
            heart.scaleTo(40, 40);
            heart.moveTo(10, 10);

            Heart heart2 = new Heart(1, 1);
            heart2.scaleTo(40, 40);
            heart2.moveTo(60, 10);
            tankHP = 3;
            //ptank.add(cannon);
            //ptank.scaleTo(50, 50);
            spawnObstacles();
        }


        while (!gewonnen) {
            spawnCactus();
            moveTank();
            shoot();
            rotateCannon();
            projectileTouchesTarget();
            moveProjectiles();
            targetTouchesTank();
            isTankDead();
            moveHindernisse();

            view.wait(SLEEP_TIME); // Das hier ist sehr wichtig, weil euer Spiel sonst zu schnell läuft.
            if (winningNumber >= spawnChance) {
                gewonnen = true;
            }

        }

        new Text(view.getWidth() / 2 - 30, view.getHeight() / 2 - 10, "Gewonnen!");
    }


    private void spawnObstacles() {
        BasicObstacle border = new BasicObstacle(1, 1, PATH_IMAGEB);
        border.scaleTo(10, HEIGHT*2);
        border.moveTo(-10, 0);
        obstacles.add(border);
        BasicObstacle borderR = new BasicObstacle(1, 1, PATH_IMAGEB);
        borderR.scaleTo(10, HEIGHT*2);
        borderR.moveTo(WIDTH, -10);
        obstacles.add(borderR);
        BasicObstacle borderO = new BasicObstacle(1, 1, PATH_IMAGEB);
        borderO.scaleTo(view.getWidth(), 10);
        borderO.moveTo(25, -10);
        obstacles.add(borderO);
        BasicObstacle borderU = new BasicObstacle(1, 1, PATH_IMAGEB);
        borderU.scaleTo(WIDTH, 10);
        borderU.moveTo(0, HEIGHT);
        obstacles.add(borderU);

        /*for (int i = 0; i<spawnChance; i++) {
            BasicObstacle obstacle = new BasicObstacle(Tools.randomNumber(1, view.getHeight()), Tools.randomNumber(1, view.getWidth()), PATH_IMAGE);
            obstacle.scaleTo(OBJECT_HEIGHT);
            obstacles.add(obstacle);
        }*/
    }

    private void spawnCactus() {
        int ranInt = Tools.randomNumber(1, spawnChance);
        System.out.println(ranInt);

        if(ranInt == 42) {
            int randomMain = Tools.randomNumber(1, 2* view.getHeight()+ 2* view.getHeight());
            //System.out.println("View" + view.getHeight() + "Width" + view.getWidth());
            System.out.println("Biggus" + randomMain);
            Trophy cactus;
            if(randomMain >= (2*view.getWidth() + view.getHeight())){
                System.out.println("Highest");
                //cactus = new Pokal(100, 100);
                cactus = new Trophy(20, randomMain-(2*view.getWidth() + view.getHeight()));
            } else if(randomMain >= (view.getWidth() + view.getHeight())){
                System.out.println("Down");
                //cactus = new Pokal(100, 100);
                cactus = new Trophy(randomMain-(view.getWidth() + view.getHeight()), view.getHeight()-160);
            } else if(randomMain >= view.getWidth()){
                System.out.println("Right");
                //cactus = new Pokal(100, 100);
                cactus = new Trophy(view.getWidth()-50, randomMain- view.getWidth());
            }
            else{
                System.out.println("Highest");
                //cactus = new Pokal(100, 100);
                cactus = new Trophy(randomMain, 20);
            }
            cactus.scaleTo(OBJECT_HEIGHT);
            hindernisse.add(cactus);
        }
    }



    double cannonRotation = 0;
    double xPosition = 200;
    double yPosition = 200;

    private void moveTank() {
        double x = controller.getLJoystickX() * SPEED_Tank;
        double y = controller.getLJoystickY() * SPEED_Tank;
        if(!checkCollision(x, y)){
            cannon.move(x, y);
            xPosition += x;
            yPosition += y;
        }
    }

    private boolean checkCollision(double x, double y) {
        tank.move(x, y);
        ArrayList<Projectile> deletableProjectiles = new ArrayList<>();
        for (BasicObstacle obstacle: obstacles){
            if(obstacle.intersects(tank)){
                System.out.println("Kollision");
                tank.move(-x, -y);
                return true;
            }
            for(Projectile bullet: bullets){
                if(obstacle.intersects(bullet)){
                    view.remove(bullet);
                    deletableProjectiles.add(bullet);
                }
            }
        }
        for (Projectile bullet : deletableProjectiles) {
            bullet.setHidden(true);
            view.remove(bullet);
            bullets.remove(deletableProjectiles);
        }

        return false;
    }

    private void moveHindernisse() {
        // Aktuelle Position des Panzers abrufen
        double tankX = tank.getShapeX();
        double tankY = tank.getShapeY();

        for (Trophy hindernis : hindernisse) {
            double xDiff = tankX - hindernis.getShapeX();
            double yDiff = tankY - hindernis.getShapeY();
            double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
            //System.out.println(hindernis.getShapeY() + "Und" + tankY);


            // Normale Richtung berechnen
            double xMove = SPEED_OBSTACLE * (xDiff / distance);
            double yMove = SPEED_OBSTACLE * (yDiff / distance);

            // Versuche, sich in die Richtung des Tanks zu bewegen
            if (!checkObstacleCollision(hindernis, xMove, yMove)) {
                hindernis.move(xMove, yMove);
            } else {
                // Probiere alternative Bewegungen, um Hindernisse zu umgehen
                double bestXMove = 0;
                double bestYMove = 0;
                double minDistance = distance;

                // Probiere alle acht Richtungen (inkl. Diagonalen)
                double[][] directions = {
                        {SPEED_OBSTACLE, 0},
                        {-SPEED_OBSTACLE, 0},
                        {0, SPEED_OBSTACLE},
                        {0, -SPEED_OBSTACLE},
                        {SPEED_OBSTACLE, SPEED_OBSTACLE},
                        {-SPEED_OBSTACLE, -SPEED_OBSTACLE},
                        {SPEED_OBSTACLE, -SPEED_OBSTACLE},
                        {-SPEED_OBSTACLE, SPEED_OBSTACLE}
                };

                for (double[] dir : directions) {
                    double newXMove = dir[0];
                    double newYMove = dir[1];
                    double newDistance = calculateDistance(hindernis.getShapeX() + newXMove, hindernis.getShapeY() + newYMove, tankX, tankY);

                    if (newDistance < minDistance && !checkObstacleCollision(hindernis, newXMove, newYMove)) {
                        bestXMove = newXMove;
                        bestYMove = newYMove;
                        minDistance = newDistance;
                    }
                }

                hindernis.move(bestXMove, bestYMove);
            }
        }
    }

    private boolean checkObstacleCollision(Trophy hindernis, double xMove, double yMove) {
        hindernis.move(xMove, yMove);
        for (BasicObstacle obstacle : obstacles) {
            if (hindernis.intersects(obstacle)) {
                // Bewege das Hindernis zurück, wenn eine Kollision erkannt wird
                hindernis.move(-xMove, -yMove);
                return true;
            }
        }
        // Bewege das Hindernis zurück, wenn keine Kollision erkannt wurde
        hindernis.move(-xMove, -yMove);
        return false;
    }

    // Hilfsmethode zur Berechnung der Distanz zwischen zwei Punkten
    private double calculateDistance(double x1, double y1, double x2, double y2) {
        double xDiff = x2 - x1;
        double yDiff = y2 - y1;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }


    private void rotateCannon() {
        double x = controller.getRJoystickX() * SPEED_Tank;
        double y = controller.getRJoystickY() * SPEED_Tank;
        if(x!=0 || y!=0) {
            double rotationNeeded = (getCannonDegrees(x, y) - cannonRotation);

            if(rotationNeeded>180){
                rotationNeeded = rotationNeeded-360;
            } else if(rotationNeeded<-180){
                rotationNeeded = rotationNeeded+360;
            }
            cannon.turn(rotationNeeded);
            updateRotation(rotationNeeded);
        }
    }

    private void updateRotation(double newRot){
        cannonRotation += newRot;
        while((cannonRotation-360)>=0){
            cannonRotation = cannonRotation-360;
        }
    }

    private void shoot(){
        if(controller.getBtn1Pressed()){
            projectile = new Projectile((int)xPosition, (int)yPosition);
            projectile.scaleTo(15);
            projectile.moveTo(xPosition, yPosition);

            projectile.turn(cannonRotation);
            bullets.add(projectile);
        }
    }

    private void moveProjectiles() {
        for (Projectile bullet : bullets) {
            bullet.move(SPEED_Projectile);
        }
    }


    private boolean projectileTouchesTarget() {
        for (Trophy target : hindernisse) {
            for (Projectile bullet: bullets) {
                if (bullet.intersects(target)) {
                    score += 1;
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

    private void targetTouchesTank() {
        for (Trophy target : hindernisse) {
            if(target.intersects(tank)){
                tankHP -= 100;
            }
        }
    }
    private void isTankDead() {
        if(tankHP<=0){
            gewonnen = true;
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
                return Math.toDegrees(Math.atan(divide(x, y)));
            } else {
                return Math.toDegrees(Math.atan(divide(x, y)));
            }
        } else {
            if (y > 0) {
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
}