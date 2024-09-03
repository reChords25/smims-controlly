package viewcontents;

import controller.AbstractController;
import sas.Text;
import sas.View;
import util.Button;

import java.awt.*;

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
        mainText.setFontSansSerif(true, 60);
        mainText.moveTo(viewCenterX - mainText.getShapeWidth() / 2, viewCenterY - mainText.getShapeHeight() / 2 - 150);

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
    public boolean tick() {
        if (exitGameButton.clicked()) {
            System.out.println("Exit Game");
            System.exit(0);
        }
        if (gameSelectionButton.clicked()) {
            ViewContents.getInstance().clear(1);
            ViewContents.getInstance().runViewContent(new GameSelectionMenu(view, controller));
            return false;
        }
        return true;
    }

}
