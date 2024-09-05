package viewcontents.demo;

import controller.AbstractController;
import sas.Rectangle;
import sas.Text;
import sas.View;
import util.Button;
import viewcontents.AbstractViewContent;
import viewcontents.GameSelectionMenu;
import viewcontents.ViewContents;

import java.awt.*;


public class Demo extends AbstractViewContent {

    private static final Color RED = new Color(255, 85, 85);
    private static final Color PURPLE = new Color(187, 85, 255);
    private static final Color GREEN = new Color(85, 255, 85);

    private Button menuButton;
    private Text stickRYValue;
    private Text stickRXValue;
    private Text stickLYValue;
    private Text stickLXValue;
    private Text rButtonText;
    private Text lButtonText;
    private Text rPadText;
    private Text lPadText;
    private Rectangle rButtonRect;
    private Rectangle lButtonRect;
    private Rectangle rPadRect;
    private Rectangle lPadRect;

    public Demo(View view, AbstractController controller) {
        super(view, controller);
    }

    @Override
    protected void initView() {
        int[][] positions = new int[][]{{125, 475}, {150, 250, 350, 450}};
        menuButton = new Button(25, 25, 300, 50, "To menu", PURPLE);

        stickRXValue = new Text(positions[0][0], positions[1][0], "");
        stickRXValue.setFontSansSerif(true, 30);
        stickRYValue = new Text(positions[0][0], positions[1][1], "");
        stickRYValue.setFontSansSerif(true, 30);
        stickLXValue = new Text(positions[0][1], positions[1][0], "");
        stickLXValue.setFontSansSerif(true, 30);
        stickLYValue = new Text(positions[0][1], positions[1][1], "");
        stickLYValue.setFontSansSerif(true, 30);

        rButtonRect = new Rectangle(positions[0][0], positions[1][2], 300, 50, RED);
        lButtonRect = new Rectangle(positions[0][1], positions[1][2], 300, 50, RED);
        rPadRect = new Rectangle(positions[0][0], positions[1][3], 300, 50, RED);
        lPadRect = new Rectangle(positions[0][1], positions[1][3], 300, 50, RED);

        rButtonText = new Text(positions[0][0] + 20, positions[1][2] + 7, "Right Stick Button");
        rButtonText.setFontSansSerif(true, 30);
        lButtonText = new Text(positions[0][1] + 20, positions[1][2] + 7, "Left Stick Button");
        lButtonText.setFontSansSerif(true, 30);
        rPadText = new Text(positions[0][0] + 20, positions[1][3] + 7, "Right Pad Button");
        rPadText.setFontSansSerif(true, 30);
        lPadText = new Text(positions[0][1] + 20, positions[1][3] + 7, "Left Pad Button");
        lPadText.setFontSansSerif(true, 30);


        buttonsToRemove.add(menuButton);
        buttonsToRemove.add(menuButton);
        buttonsToRemove.add(menuButton);
        buttonsToRemove.add(menuButton);
        textsToRemove.add(stickRXValue);
        textsToRemove.add(stickRYValue);
        textsToRemove.add(stickLXValue);
        textsToRemove.add(stickLYValue);
        textsToRemove.add(rButtonText);
        textsToRemove.add(lButtonText);
        textsToRemove.add(rPadText);
        textsToRemove.add(lPadText);
        shapesToRemove.add(rButtonRect);
        shapesToRemove.add(lButtonRect);
        shapesToRemove.add(rPadRect);
        shapesToRemove.add(lPadRect);
    }

    @Override
    public boolean tick() {
        stickRXValue.setText("Right Joystick X:   " + controller.getRJoystickX());
        stickRYValue.setText("Right Joystick Y:   " + controller.getRJoystickY());
        stickLXValue.setText("Left Joystick X:   " + controller.getLJoystickX());
        stickLYValue.setText("Left Joystick Y:   " + controller.getLJoystickY());
        rButtonRect.setColor(controller.getRJoystickButton() ? GREEN : RED);
        lButtonRect.setColor(controller.getLJoystickButton() ? GREEN : RED);
        rPadRect.setColor(controller.getRPad() ? GREEN : RED);
        lPadRect.setColor(controller.getLPad() ? GREEN : RED);
        if (menuButton.clicked()) {
            ViewContents.getInstance().clear(1);
            ViewContents.getInstance().runViewContent(new GameSelectionMenu(view, controller));
            return false;
        }
        return true;
    }
}
