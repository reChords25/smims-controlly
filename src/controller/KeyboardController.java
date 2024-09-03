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

        double x = 0.0;

        if (view.keyPressed('A')) {
            x -= 1.0;
        }

        if (view.keyPressed('D')) {
            x += 1.0;
        }

        return x;
    }
    @Override
    public double getRJoystickY() {
        double y = 0.0;

        if (view.keyPressed('W')) {
            y -= 1.0;
        }

        if (view.keyPressed('S')) {
            y += 1.0;
        }

        return y;
    }

    @Override
    public boolean getBtn1Pressed() {
        return view.keyPressed('E');
    }
}
