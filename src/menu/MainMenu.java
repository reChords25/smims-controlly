package menu;

import controller.AbstractController;
import sas.Text;
import sas.View;
import sasio.Button;

import java.awt.*;
import java.io.PrintStream;

public class MainMenu extends AbstractMenu {

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
        exitGameButton = gameSelectionButton = new Button(
                viewCenterX - buttonWidth / 2,
                viewCenterY - buttonHeight / 2 + 40,
                buttonWidth,
                buttonHeight,
                "Exit Game",
                new Color(255, 85, 85)
        );
    }

    @Override
    public void run() {
        while (true) {
            if (exitGameButton.clicked()) {
                System.out.println("Exit Game");
                System.exit(0);
            }
            view.wait(50);
        }
    }
}
