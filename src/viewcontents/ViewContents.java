package viewcontents;

import controller.AbstractController;
import sas.View;

import java.util.Stack;

public class ViewContents {

    private static ViewContents instance;
    public static final int TICK_RATE = 20;

    private AbstractViewContent vc;
    private Stack<AbstractViewContent> viewStack;

    public static ViewContents getInstance() {
        if (instance == null) {
            throw new RuntimeException("No instance found!");
        }
        return instance;
    }

    public static void initInstance(View view, AbstractController controller) {
        if (instance != null) {
            throw new IllegalStateException("MainMenu has already been initialized!");
        }
        instance = new ViewContents();
    }

    public ViewContents(){
        viewStack = new Stack<>();
    }


    public void clear()  {
        if (viewStack.peek() == null) { return; }
        viewStack.peek().removeUiElements();
    }

    public void runViewContent(AbstractViewContent vc) {
        viewStack.push(vc);
        boolean active = true;
        while (active) {
            active = viewStack.peek().tick();
            viewStack.peek().view.wait(TICK_RATE);
        }
        viewStack.pop();
    }
}
