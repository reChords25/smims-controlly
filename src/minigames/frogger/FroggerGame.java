package minigames.frogger;

import controller.AbstractController;
import minigames.AbstractGame;
import minigames.frogger.obstacles.Car;
import minigames.frogger.obstacles.Obstacle;
import sas.Text;
import sas.Tools;
import sas.View;

import java.util.ArrayList;

public class FroggerGame extends AbstractGame {

    private static final int SLEEP_TIME = 20;
    private static final int SPEED_OBSTACLE = 15;
    private static final double SPEED_FROG = 10;
    private static final double OBJECT_HEIGHT = 50;

    private boolean won;
    private Frog frog;
    private ArrayList<Obstacle> obstacles;

    /**
     * The percentage for a frog to spawn.
     */
    private int spawnChance;

    public FroggerGame(AbstractController controller, View view) {
        super(controller, view);

        this.frog = null;
        this.obstacles = new ArrayList<>();
        this.spawnChance = 10;
    }

    @Override
    protected void initView() {
        view.setName("SMIMS Frogger");

        // TODO: Clear the old game, if there is still one on screen.

    }

    @Override
    public void run() {

        won = false;

        // Spawn the trophy
        ScalablePicture trophy = new Trophy(Tools.randomNumber(60, view.getWidth() - 60), 50);
        trophy.scaleTo(OBJECT_HEIGHT);

        // Spawn the frog
        frog = new Frog(Tools.randomNumber(30, view.getWidth() - 30), view.getHeight() - 100);
        frog.scaleTo(OBJECT_HEIGHT);

        // Game loop
        while (!won) {

            spawnObstacles();
            moveObstacles();
            moveFrog();

            // Resets the frog's position if it is touching an obstacle
            if (frogTouchesObstacle()) {
                frog.moveTo(Tools.randomNumber(30, view.getWidth() - 30), view.getHeight() - 30);
            }

            won = frog.intersects(trophy);

            view.wait(SLEEP_TIME); // Wait

        }

        new Text(view.getWidth() / 2 - 30, view.getHeight() / 2 - 10, "Won!");

    }


    private void spawnObstacles() {

        // Randomly generate the obstacles
        // --> like this, they don't become too many
        if (Tools.randomNumber(1, 100) <= spawnChance) {
            Obstacle obstacle = new Car(-200, Tools.randomNumber(view.getHeight() - 150, 30));
            obstacle.scaleTo(OBJECT_HEIGHT);
            obstacles.add(obstacle);
        }

    }

    private void moveObstacles() {
        ArrayList<Obstacle> obstaclesToRemove = new ArrayList<>();

        for (Obstacle obstacle : obstacles) {
            obstacle.move(SPEED_OBSTACLE);

            // Remove obstacle if it has reached the end of the window.
            if (obstacle.getShapeX() > view.getWidth()) {
                obstaclesToRemove.add(obstacle);
            }
        }

        // Remove all obstacles that are not visible anymore
        for (Obstacle obstacle : obstaclesToRemove) {
            obstacle.setHidden(true);
            view.remove(obstacle);
            obstacles.remove(obstaclesToRemove);
        }
    }

    private void moveFrog() {
        double x = controller.getJoystickX() * SPEED_FROG;
        double y = controller.getJoystickY() * SPEED_FROG;

        frog.move(x, y);
    }

    private boolean frogTouchesObstacle() {

        // Check whether the frog collides with an obstacle
        for (Obstacle hindernis : obstacles) {
            if (frog.intersects(hindernis)) {
                return true;
            }
        }
        return false;
    }
}
