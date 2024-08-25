package controller;

import sas.View;

/**
 * Die Klasse <code>{@link TastaturController}</code> dient lediglich als Platzhalter während der Programmierphase,
 * solange Joystick, Gamepad, ... noch nicht fertiggestellt sind. Damit die anderen Teams programmieren und ihre Spiele
 * auch steuern können, wird übergangsweise die Tastatur zur Eingabe verwendet. Später kann man dann einfach zur
 * Joystick-/Gamepad-/... Steuerung wechseln, indem man schlicht die verwendete Klasse ändert.
 */
public class TastaturController extends AbstractController {

    /* Static Variables */

    /* Static Methods */

    /* Object Variables */
    private View view;

    /* Constructors */
    public TastaturController(View view) {
        this.view = view;
    }

    /* Object Methods */
    @Override
    protected void werteDatenAus() {
        // Hier muss nichts getan werden.
    }

    @Override
    public void disconnect() {
        // Hier muss nichts getan werden.
    }

    /* Getters and Setters */

    /**
     * In dieser Beispielmethode wird gezeigt, wie man mithilfe der Tastatureingabe (Pfeiltaste links und / oder
     * Pfeiltaste rechts) das Joystick- bzw. Gamepad-Input simulieren könnte.
     *
     * @return
     */
    @Override
    public double getJoystickX() {

        double x = 0.0;

        if (view.keyLeftPressed()) {
            x -= 1.0;
        }

        if (view.keyRightPressed()) {
            x += 1.0;
        }

        return x;
    }

    @Override
    public double getJoystickY() {
        double y = 0.0;

        if (view.keyUpPressed()) {
            y -= 1.0;
        }

        if (view.keyDownPressed()) {
            y += 1.0;
        }

        return y;
    }

    @Override
    public boolean getA() {
        return view.keyPressed('A');
    }

    @Override
    public boolean getB() {
        return view.keyPressed('B');
    }

    /* Inner Classes */

}
