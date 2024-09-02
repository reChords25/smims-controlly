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

}
