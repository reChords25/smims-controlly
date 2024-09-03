package viewcontents;

import controller.AbstractController;
import sas.Shapes;
import sas.Text;
import sas.View;
import util.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class AbstractViewContent {

    public static final String PATH_TO_RESOURCES = "resources/";

    protected View view;
    protected AbstractController controller;
    protected ArrayList<Shapes> shapesToRemove;
    protected ArrayList<Button> buttonsToRemove;
    protected ArrayList<Text> textsToRemove;

    public AbstractViewContent(View view, AbstractController controller){
        this.controller = controller;
        this.view = view;
        this.shapesToRemove = new ArrayList<>();
        this.buttonsToRemove = new ArrayList<>();
        this.textsToRemove = new ArrayList<>();
        initView();
    }


    protected abstract void initView();

    public abstract boolean tick();

    protected void removeUiElements() {

        for (Shapes shape : shapesToRemove) {
            shape.setHidden(true);
            view.remove(shape);
        }
        for(Button button : buttonsToRemove) {
            button.setHidden(true);
        }
        for(Text text : textsToRemove) {
            text.setHidden(true);
        }
    }
}
