package controller;

import sas.View;

/**
 The class <code>{@link KeyboardController}</code> serves merely as a placeholder during the programming phase,
 as long as the joystick, gamepad, etc., are not yet completed.
 In the meantime, the keyboard is being used for input so that other teams can program and control their games.
 Later, one can easily switch to joystick/gamepad/... control by simply changing the class being used.
 */
public class KeyboardController extends AbstractController {

    private View view;

    public KeyboardController(View view) {
        this.view = view;
    }

    @Override
    protected void evalData() {}

    @Override
    public void disconnect() {}

    @Override
    public void sendLights(int l1, int l2, int l3, int l4, int l5, int l6, int l7, int l8) {

    }

    @Override
    protected void sendVibration(boolean on) {

    }

    @Override
    public double getLJoystickX() {

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
    public double getLJoystickY() {
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
    public double getRJoystickX() {
        return 0;
    }

    @Override
    public double getRJoystickY() {
        return 0;
    }

    @Override
    public boolean getA() {
        return view.keyPressed('A');
    }

    @Override
    public boolean getB() {
        return view.keyPressed('B');
    }

}
