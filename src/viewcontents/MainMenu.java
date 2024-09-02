package viewcontents;

import com.sun.tools.javac.Main;
import controller.AbstractController;
import sas.Shapes;
import sas.Text;
import sas.View;
import util.Button;

import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends AbstractViewContent {

    private Text mainText;

    private Button gameSelectionButton;
    private Button exitGameButton;

    public MainMenu(View view, AbstractController controller) {
        super(view, controller);
    }

    @Override
    protected void initView() {
        int viewCenterX = view.getWidth() / 2;
        int viewCenterY = view.getHeight() / 2;
        int buttonWidth = 400;
        int buttonHeight = 50;

        mainText = new Text(0, 0, "Welcome!", Color.BLACK);
        mainText.moveTo(viewCenterX - mainText.getShapeWidth() / 2, viewCenterY - mainText.getShapeHeight() / 2 - 100);

        gameSelectionButton = new Button(
                viewCenterX - buttonWidth / 2,
                viewCenterY - buttonHeight / 2 - 40,
                buttonWidth,
                buttonHeight,
                "Select Game",
                new Color(85, 255, 85)
        );

        exitGameButton = new Button(
                viewCenterX - buttonWidth / 2,
                viewCenterY - buttonHeight / 2 + 40,
                buttonWidth,
                buttonHeight,
                "Exit Game",
                new Color(255, 85, 85)
        );

        buttonsToRemove.add(gameSelectionButton);
        buttonsToRemove.add(exitGameButton);
        textsToRemove.add(mainText);
    }

    @Override
    public void run() {
        while (true) {
            if (exitGameButton.clicked()) {
                System.out.println("Exit Game");
                System.exit(0);
            }
            if (gameSelectionButton.clicked()) {
                removeUiElements();
                ViewContents.getInstance().setViewContent(new GameSelectionMenu(view, controller));
            }
            view.wait(50);
        }
    }

}
