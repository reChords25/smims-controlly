package viewcontents.tank;

import controller.AbstractController;
import sas.Text;
import sas.Tools;
import sas.View;
import util.GameOverLay;
import viewcontents.AbstractViewContent;
import viewcontents.ViewContents;

import java.lang.Math;

import java.util.ArrayList;


public class CactiCatchGame extends AbstractViewContent {

    private static final String cactusOne = AbstractViewContent.PATH_TO_RESOURCES + "CactiCatch/cactusEins.png";
    private static final String cactusTwo = AbstractViewContent.PATH_TO_RESOURCES + "CactiCatch/cactusTwo.png";
    private static final String cactusThree = AbstractViewContent.PATH_TO_RESOURCES + "CactiCatch/cactusThree.png";

    /* Static Variables */
    private static final int SPEED_Projectile = 25;
    private static boolean gameOver;
    private static int points = 0;

    public int tankHP;
    private double SPEED_OBSTACLE = 1;
    private double SPEED_Tank = 3;

    private Heart heart1;
    private Heart heart2;
    private Heart heart3;
    private Text scoreboard;


    SoundPlayer steps = new SoundPlayer("CactiCatch/steps2.wav");
    SoundPlayer loseSound = new SoundPlayer("CactiCatch/damage.wav");
    SoundPlayer hitSound = new SoundPlayer("CactiCatch/damage2.wav");
    SoundPlayer shootSound = new SoundPlayer("CactiCatch/wandSound5.wav");

    private SoundPlayer backgroundMusic;
    private Background background;
    private Player tank;
    private Cannon cannon;
    private Projectile projectile;
    private ArrayList<Projectile> bullets;
    private ArrayList<Trophy> hindernisse;
    private ArrayList<BasicObstacle> obstacles;


    private boolean isRunning;
    private int spawnChance;
    private int spawnChance2;
    private int spawnChance3;
    private int score;
    private int level;
    private int clock = 0;

    /* Constructors */
    public CactiCatchGame(View view, AbstractController controller) {
        super(view, controller);



        this.tank = null;
        this.bullets = new ArrayList<>();
        this.hindernisse = new ArrayList<>();
        this.obstacles = new ArrayList<>();
        this.spawnChance = 200;
        this.spawnChance2 = 10000;
        this.spawnChance3 = 10000;

        this.tankHP = 3;
        this.SPEED_OBSTACLE = 1;

    }

    /* Object Methods */
    @Override
    protected void initView() {

        view.setName("SMIMS TankGame");

    }

    boolean hasRunOnce = false;

    @Override
    public boolean tick() {
        if (!hasRunOnce) {
            isRunning = false;
            hasRunOnce = true;
            gameOver = false;

            background = new Background(0, 0);
            shapesToRemove.add(background);
            //System.out.println(view.getWidth());
            background.scaleTo(view.getWidth(), view.getHeight());
            background.moveTo(0, 0);

            // Spawns the Tank


            tank = new Player(200, 200);
            shapesToRemove.add(tank);
            tank.scaleTo(37, 55);
            tank.moveTo(200, 200);

            //ptank = new Sprite(tank);
            cannon = new Cannon(200, 200);
            shapesToRemove.add(cannon);
            cannon.scaleTo(20, 20);
            cannon.moveTo(232, 222);

            tankHP = 3;
            heart1 = new Heart(1, 1);
            shapesToRemove.add(heart1);
            heart1.scaleTo(40, 40);
            heart1.moveTo(10, 10);

            heart2 = new Heart(1, 1);
            shapesToRemove.add(heart2);
            heart2.scaleTo(40, 40);
            heart2.moveTo(60, 10);

            heart3 = new Heart(1, 1);
            shapesToRemove.add(heart3);
            heart3.scaleTo(40, 40);
            heart3.moveTo(110, 10);

            level = 0;
            scoreboard = new Text(view.getWidth() - 90, -10, "Score: 0 | Level 1");
            textsToRemove.add(scoreboard);
            scoreboard.scaleTo(240, 90);
            //ptank.add(cannon);
            //ptank.scaleTo(50, 50);
            spawnObstacles();
            backgroundMusic = new SoundPlayer("CactiCatch/background_music.wav");
            backgroundMusic.loop();
            backgroundMusic.setVolume(-10f);
            steps.setVolume(6f);
        }

        if (!gameOver) {
            if (score >= 10) {
                spawnChance = 250;
                spawnChance2 = 250;
                level = 2;
            } else if (score >= 20) {
                spawnChance = 300;
                spawnChance2 = 300;
                spawnChance3 = 300;
                level = 3;
            }else if (score >= 30) {
                spawnChance = 300;
                spawnChance2 = 200;
                spawnChance3 = 200;
                level = 4;
            }
            clock += 1;
            updateText(level, score);

            spawnCactus();
            spawnCactus2();
            spawnCactus3();
            moveTank();
            shoot();
            rotateCannon();

            projectileTouchesTarget();
            moveProjectiles();
            targetTouchesTank();
            isTankDead();
            moveHindernisse();

            if (points == 2147483647) {
                gameOver = true;
            }
        }
        if(gameOver){
            steps.stop();
            backgroundMusic.stop();
            loseSound.play();
            GameOverLay overlay = new GameOverLay(view, controller);
            overlay.setGameData(this.getClass(), false);
            ViewContents.getInstance().runViewContent(overlay);
        }
        return !gameOver;
    }


    private void updateText(int level, int score) {
        view.remove(scoreboard);
        scoreboard = new Text(view.getWidth() - 180, -10, "Score: " + score + " | Level " + level);
        textsToRemove.add(scoreboard);
        scoreboard.scaleTo(430, 90);
    }

    /*public void speichern(String dateiname, String datensatz) {
        if (!StringFileTools.fileExists(dateiname)) {
            StringFileTools.writeInFile(dateiname,datensatz);
        } else {
            StringFileTools.appendToFile(dateiname, datensatz);
        }
    }*/


    private void spawnObstacles() {
        BasicObstacle fountain = new BasicObstacle(1, 1, AbstractViewContent.PATH_TO_RESOURCES + "cacticatch/fountain.png");
        shapesToRemove.add(fountain);
        fountain.scaleTo(126, 126);
        fountain.moveTo((view.getWidth()/2)-60, (view.getHeight()/2)-70);
        obstacles.add(fountain);
        BasicObstacle treeOne = new BasicObstacle(1, 1, AbstractViewContent.PATH_TO_RESOURCES + "/cacticatch/tree_small_01.png");
        shapesToRemove.add(treeOne);
        treeOne.scaleTo(30);
        treeOne.moveTo(80, 140);
        obstacles.add(treeOne);
        BasicObstacle treeTwo = new BasicObstacle(1, 1, AbstractViewContent.PATH_TO_RESOURCES + "/cacticatch/tree_small_02.png");
        shapesToRemove.add(treeTwo);
        treeTwo.scaleTo(30);
        treeTwo.moveTo(150, 40);
        obstacles.add(treeTwo);
        BasicObstacle treeThree = new BasicObstacle(1, 1, AbstractViewContent.PATH_TO_RESOURCES + "/cacticatch/tree_small_03.png");
        shapesToRemove.add(treeThree);
        treeThree.scaleTo(30);
        treeThree.moveTo(200, 70);
        obstacles.add(treeThree);
    }

    private void spawnCactus() {
        int ranInt = Tools.randomNumber(1, spawnChance);
        if (ranInt == 2) {
            int randomMain = Tools.randomNumber(1, 2 * view.getHeight() + 2 * view.getHeight());
            ;
            if (randomMain >= (2 * view.getWidth() + view.getHeight())) {
                createCactus(1, -10, randomMain - (2 * view.getWidth() + view.getHeight()));
            } else if (randomMain >= (view.getWidth() + view.getHeight())) {
                createCactus(1, randomMain - (view.getWidth() + view.getHeight()), view.getHeight() +10);
            } else if (randomMain >= view.getWidth()) {
                createCactus(1, view.getWidth() + 10, randomMain - view.getWidth());
            } else {
                createCactus(1, randomMain, -10);
            }
        }
    }

    private void spawnCactus2() {
        int ranInt = Tools.randomNumber(1, spawnChance2);
        if (ranInt == 2) {
            int randomMain = Tools.randomNumber(1, 2 * view.getHeight() + 2 * view.getHeight());
            ;
            if (randomMain >= (2 * view.getWidth() + view.getHeight())) {
                createCactus(2, 30, randomMain - (2 * view.getWidth() + view.getHeight()));
            } else if (randomMain >= (view.getWidth() + view.getHeight())) {
                createCactus(2, randomMain - (view.getWidth() + view.getHeight()), view.getHeight() - 160);
            } else if (randomMain >= view.getWidth()) {
                createCactus(2, view.getWidth() - 50, randomMain - view.getWidth());
            } else {
                createCactus(2, randomMain, 20);
            }
        }
    }

    private void spawnCactus3() {
        int ranInt = Tools.randomNumber(1, spawnChance);
        if (ranInt == 2) {
            int randomMain = Tools.randomNumber(1, 2 * view.getHeight() + 2 * view.getHeight());

            if (randomMain >= (2 * view.getWidth() + view.getHeight())) {
                createCactus(3, 30, randomMain - (2 * view.getWidth() + view.getHeight()));

            } else if (randomMain >= (view.getWidth() + view.getHeight())) {
                createCactus(3, randomMain - (view.getWidth() + view.getHeight()), view.getHeight() - 160);
            } else if (randomMain >= view.getWidth()) {
                createCactus(3, view.getWidth() - 50, randomMain - view.getWidth());
            } else {
                createCactus(3, randomMain, 20);
            }
        }
    }


    double cannonRotation = 0;
    double xPosition = 200;
    double yPosition = 200;

    private void moveTank() {
//        double rawX = 512-(controller.getLJoystickX());
//        double rawY = 512-(controller.getLJoystickY());
//    //System.out.println(rawX);
//
//        double x = (rawX/ 100);
//        double y = (rawY/100);
//        if((x<1 && x>0) || (x<0 && x>-1)){
//            x = 0;
//        }
//        if((y<1 && y>0) || (y<0 && y>-1)){
//            y = 0;
//        }
        //System.out.println(x);
        //System.out.println(y);
        //System.out.println("Dings: " + rawX + "Uepselon" + rawY);

        double x = controller.getLJoystickX() * SPEED_Tank;
        double y = controller.getLJoystickY() * SPEED_Tank;

        if (!checkCollision(x, y)) {
            cannon.move(x, y);
            xPosition += x;
            yPosition += y;
            if( x!= 0 || y != 0){
                if(!isRunning){
                    isRunning = true;
                    steps.loop();
                }
            } else{
                if(isRunning){
                    isRunning = false;
                    steps.stop();
                }
            }
        }

        if (!checkCollision(x, y)) {
            cannon.move(x, y);
            xPosition += x;
            yPosition += y;
        }
    }

    private boolean checkCollision(double x, double y) {
        tank.move(x, y);
        ArrayList<Projectile> deletableProjectiles = new ArrayList<>();
        if (background.contains(tank)) {
            for (BasicObstacle obstacle : obstacles) {
                if (obstacle.intersects(tank)) {
                    //System.out.println("Kollision");
                    tank.move(-x, -y);
                    return true;
                }
                for (Projectile bullet : bullets) {
                    if (obstacle.intersects(bullet)) {
                        view.remove(bullet);
                        deletableProjectiles.add(bullet);
                    }
                }
            }
            for(Projectile bullet : bullets) {
                if(!background.contains(bullet)){
                    view.remove(bullet);
                    deletableProjectiles.add(bullet);
                }
            }
        } else {
            tank.move(-x, -y);
            return true;
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
            //System.out.println("print");
            double xDiff = tankX - hindernis.getShapeX();
            double yDiff = tankY - hindernis.getShapeY();
            double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);
            //System.out.println(hindernis.getShapeY() + "Und" + tankY);


            // Normale Richtung berechnen
            double xMove = hindernis.getSpeed() * SPEED_OBSTACLE * (xDiff / distance);
            double yMove = hindernis.getSpeed() * SPEED_OBSTACLE * (yDiff / distance);
            //System.out.println(xMove);

            // Versuche, sich in die Richtung des Tanks zu bewegen
            if (!checkObstacleCollision(hindernis, xMove, yMove)) {
                System.out.println("1");
                hindernis.move(xMove, yMove);
            } else if (!checkObstacleCollision(hindernis, xMove, -yMove)) {
                System.out.println("2");
                hindernis.move(xMove, -yMove);
            }
            else if (!checkObstacleCollision(hindernis, -xMove, yMove)) {
                System.out.println("3");
                hindernis.move(-xMove, yMove);
            }
            else if (!checkObstacleCollision(hindernis, hindernis.getSpeed(), 0)) {
                System.out.println("4");
                hindernis.move(hindernis.getSpeed(), 0);
            }
            else if (!checkObstacleCollision(hindernis, -hindernis.getSpeed(), 0)) {
                System.out.println("5");
                hindernis.move(-hindernis.getSpeed(), 0);
            }
            else if (!checkObstacleCollision(hindernis, 0, -hindernis.getSpeed())) {
                System.out.println("6");
                hindernis.move(0, -hindernis.getSpeed());
            }else if (!checkObstacleCollision(hindernis, 0, hindernis.getSpeed())) {
                System.out.println("7");
                hindernis.move(0, hindernis.getSpeed());
            }
//                // Probiere alternative Bewegungen, um Hindernisse zu umgehen
//                double bestXMove = 0;
//                double bestYMove = 0;
//                double minDistance = distance;
//
//                // Probiere alle acht Richtungen (inkl. Diagonalen)
//                double[][] directions = {
//                        {SPEED_OBSTACLE, 0},
//                        {-SPEED_OBSTACLE, 0},
//                        {0, SPEED_OBSTACLE},
//                        {0, -SPEED_OBSTACLE},
//                        {SPEED_OBSTACLE, SPEED_OBSTACLE},
//                        {-SPEED_OBSTACLE, -SPEED_OBSTACLE},
//                        {SPEED_OBSTACLE, -SPEED_OBSTACLE},
//                        {-SPEED_OBSTACLE, SPEED_OBSTACLE}
//                };
//
//                for (double[] dir : directions) {
//                    double newXMove = dir[0];
//                    double newYMove = dir[1];
//                    double newDistance = calculateDistance(hindernis.getShapeX() + newXMove, hindernis.getShapeY() + newYMove, tankX, tankY);
//
//                    if (newDistance < minDistance && !checkObstacleCollision(hindernis, newXMove, newYMove)) {
//                        bestXMove = newXMove;
//                        bestYMove = newYMove;
//                        minDistance = newDistance;
//                    }
//                }

//                hindernis.move(bestXMove, bestYMove);
//            }
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
        if (hindernis.intersects(tank)) {
            hindernis.move(-xMove, -yMove);
            takeDamage(hindernis);
            return true;
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

        //double rawX = 512-(controller.getRJoystickX());
        //double rawY = 512-(controller.getRJoystickY());
        //System.out.println(rawX);

        // double x = (rawX/ 50);
        //double y = (rawY/50);

        if (x != 0 || y != 0) {
            double rotationNeeded = (getCannonDegrees(x, y) - cannonRotation);

            if (rotationNeeded > 180) {
                rotationNeeded = rotationNeeded - 360;
            } else if (rotationNeeded < -180) {
                rotationNeeded = rotationNeeded + 360;
            }
            cannon.turn(rotationNeeded);
            updateRotation(rotationNeeded);
        }
    }

    private void updateRotation(double newRot) {
        cannonRotation += newRot;
        while ((cannonRotation - 360) >= 0) {
            cannonRotation = cannonRotation - 360;
        }
    }

    private int lastShot = 0;

    private void shoot() {
        if (controller.getLPad() && (clock - lastShot) >= 10) {
            shootSound.play();
            lastShot = clock;
            projectile = new Projectile((int) xPosition, (int) yPosition);
            shapesToRemove.add(projectile);
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
            for (Projectile bullet : bullets) {
                if (bullet.intersects(target)) {
//                    if(target.getHp == 1){
//
//                    }

                    score += 1;
                    view.remove(target);
                    hindernisse.remove(target);
                    view.remove(bullet);
                    bullets.remove(bullet);
                    points = points + 1;
                    return true;
                }
            }
        }
        return false;
    }

    private void targetTouchesTank() {
        for (Trophy target : hindernisse) {
            if (target.intersects(tank)) {
                takeDamage(target);
            }
        }
    }

    private void takeDamage(Trophy target) {
        if ((clock - target.getLastDamage()) > 25) {
            target.setLastDamage(clock);
            hitSound.play();
            tankHP -= target.getDamage();
            updateHearts(false);
        }

    }

    private void isTankDead() {
        if (tankHP <= 0) {
            gameOver = true;
        }
    }

    private void updateHearts(boolean increased) {
        if (tankHP == 3) {
            heart3 = new Heart(1, 1);
            shapesToRemove.add(heart3);
            heart3.scaleTo(40, 40);
            heart3.moveTo(110, 10);
        } else if (tankHP == 2) {
            if (!increased) {
                view.remove(heart3);
            } else {
                heart2 = new Heart(1, 1);
                shapesToRemove.add(heart2);
                heart2.scaleTo(40, 40);
                heart2.moveTo(60, 10);
            }
        } else if (tankHP == 1) {
            if (!increased) {
                view.remove(heart2);
                if (heart3 != null) {
                    view.remove(heart3);
                }
            } else {
                heart1 = new Heart(1, 1);
                shapesToRemove.add(heart1);
                heart1.scaleTo(40, 40);
                heart1.moveTo(10, 10);
            }
        } else {
            view.remove(heart1);

            if (heart2 != null) {
                view.remove(heart2);
            }
        }
        if (tankHP == 2 && !increased) {
            view.remove(heart3);
        }
    }

    private void createCactus(int cactusType, double posX, double posY) {
        if (cactusType == 1) {
            Trophy cactus = new Trophy(1, 1, cactusOne);
            shapesToRemove.add(cactus);
            cactus.setHp(1);
            cactus.setDamage(1);
            cactus.setSpeed(4);
            cactus.scaleTo(50, 50);
            cactus.moveTo(posX, posY);
            hindernisse.add(cactus);
        } else if (cactusType == 2) {
            Trophy cactus = new Trophy(1, 1, cactusThree);
            shapesToRemove.add(cactus);
            cactus.setHp(1);
            cactus.setDamage(2);
            cactus.setSpeed(2);
            cactus.scaleTo(50, 50);
            cactus.moveTo(posX, posY);
            hindernisse.add(cactus);
        } else {
            Trophy cactus = new Trophy(1, 1, cactusTwo);
            shapesToRemove.add(cactus);
            cactus.setHp(3);
            cactus.setDamage(2);
            cactus.setSpeed(2);
            cactus.scaleTo(50, 50);
            cactus.moveTo(posX, posY);
            hindernisse.add(cactus);
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