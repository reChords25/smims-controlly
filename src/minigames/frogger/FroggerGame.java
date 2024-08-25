package minigames.frogger;

import controller.AbstractController;
import minigames.AbstractGame;
import minigames.frogger.obstacles.Auto;
import minigames.frogger.obstacles.Obstacle;
import sas.Text;
import sas.Tools;
import sas.View;

import java.util.ArrayList;

public class FroggerGame extends AbstractGame {

    /* Static Variables */
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private static final int SLEEP_TIME = 20;
    private static final int SPEED_OBSTACLE = 15;
    private static final double SPEED_FROG = 10;
    private static final double OBJECT_HEIGHT = 50;


    /* Static Methods */

    /* Object Variables */
    private boolean gewonnen;
    private Frog frosch;
    private ArrayList<Obstacle> hindernisse;
    /**
     * Gibt an, mit welcher Wahrscheinlichkeit ein Hindernis spawnt (in Prozent).
     */
    private int spawnChance;

    /* Constructors */
    public FroggerGame(AbstractController controller, View view) {
        super(controller, view);

        this.frosch = null;
        this.hindernisse = new ArrayList<>();
        this.spawnChance = 10;
    }

    /* Object Methods */
    @Override
    protected void initView() {

        view.setSize(WIDTH, HEIGHT);
        view.setName("SMIMS Frogger");

        // TODO: Was tue ich, wenn noch ein vorheriges Spiel auf dem Bildschirm zu sehen ist? Ich muss ihn irgendwie 'clearen'.

    }

    @Override
    public void runGame() {

        gewonnen = false;

        // Erzeuge den Pokal, zu dem wir hinlaufen müssen.
        ScalablePicture pokal = new Pokal(Tools.randomNumber(60, view.getWidth() - 60), 50);
        pokal.scaleTo(OBJECT_HEIGHT);

        // Erzeuge den Frosch.
        frosch = new Frog(Tools.randomNumber(30, view.getWidth() - 30), view.getHeight() - 100);
        frosch.scaleTo(OBJECT_HEIGHT);

        // Los geht's!
        while (!gewonnen) {

            spawneHindernisse();
            bewegeHindernisse();
            bewegeFrosch();

            // Falls der Frosch eines der Hindernisse berührt, wird er wieder an den Anfang zurückgesetzt.
            if (froschBeruehrtHindernis()) {
                frosch.moveTo(Tools.randomNumber(30, view.getWidth() - 30), view.getHeight() - 30);
            }

            // Haben wir gewonnen?
            gewonnen = frosch.intersects(pokal);

            view.wait(SLEEP_TIME); // Das hier ist sehr wichtig, weil euer Spiel sonst zu schnell läuft.

        }

        new Text(view.getWidth() / 2 - 30, view.getHeight() / 2 - 10, "Gewonnen!");

    }


    private void spawneHindernisse() {

        // Damit wir uns nicht den kompletten Bildschirm mit Hindernissen 'zuballern', verwenden wir einen Zufalls-
        // generator, der tatsächlich die meisten Runden ausspart.
        if (Tools.randomNumber(1, 100) <= spawnChance) {
            Obstacle hindernis = new Auto(-200, Tools.randomNumber(view.getHeight() - 150, 30));
            hindernis.scaleTo(OBJECT_HEIGHT);
            hindernisse.add(hindernis);
        }

    }

    private void bewegeHindernisse() {

        ArrayList<Obstacle> zuEntfernendeHindernisse = new ArrayList<>();

        for (Obstacle hindernis : hindernisse) {
            hindernis.move(SPEED_OBSTACLE);

            // Haben wir den Rand erreicht? Dann können wir das Hindernis aus dem Spiel entfernen.
            if (hindernis.getShapeX() > view.getWidth()) {
                zuEntfernendeHindernisse.add(hindernis);
            }
        }

        // Entferne alle Hindernisse, die nicht mehr zu sehen sind.
        for (Obstacle hindernis : zuEntfernendeHindernisse) {
            hindernis.setHidden(true);
            view.remove(hindernis);
            hindernisse.remove(zuEntfernendeHindernisse);
        }
    }

    private void bewegeFrosch() {

        double x = controller.getJoystickX() * SPEED_FROG;
        double y = controller.getJoystickY() * SPEED_FROG;

        frosch.move(x, y);

    }

    private boolean froschBeruehrtHindernis() {

        // Wir müssen für jedes Hindernis schauen, ob der Frosch es berührt.
        for (Obstacle hindernis : hindernisse) {
            if (frosch.intersects(hindernis)) {
                return true;
            }
        }

        return false;

    }

    /* Getters and Setters */

    /* Inner Classes */

}
