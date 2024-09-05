package controller;

import sas.View;

/**
 * The class <code>{@link KeyboardController}</code> serves merely as a placeholder during the programming phase,
 * as long as the joystick, gamepad, etc., are not yet completed.
 * In the meantime, the keyboard is being used for input so that other teams can program and control their games.
 * Later, one can easily switch to joystick/gamepad/... control by simply changing the class being used.
 */
public class KeyboardController extends AbstractController {

    private View view;

    public KeyboardController(View view) {
        this.view = view;
    }

    @Override
    protected void evalData() {
    }

    @Override
    public void disconnect() {
    }

    @Override
    public int getLJoystickX() {
        if (view.keyRightPressed()) {
            return 1;
        }
        if (view.keyLeftPressed()) {
            return -1;
        }
        return 0;
    }

    @Override
    public int getLJoystickY() {
        if (view.keyUpPressed()) {
            return -1;
        }
        if (view.keyDownPressed()) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean getLJoystickButton() {
        return false;
    }

    @Override
    public boolean getLPad() {
        return view.keyPressed('l');
    }

    @Override
    public int getRJoystickX() {
        return 0;
    }

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
    public int getRJoystickY() {
        return 0;
    }

    @Override
    public boolean getRJoystickButton() {
        return false;
    }

    @Override
    public boolean getRPad() {
        return false;
    }

    @Override
    public boolean getBtn1Pressed() {
        return view.keyPressed('E');
    }
}
