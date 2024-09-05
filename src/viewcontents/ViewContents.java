package viewcontents;

import controller.AbstractController;
import sas.View;

import java.util.Stack;

public class ViewContents {

    private static ViewContents instance;
    public static final int TICK_RATE = 500;

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


    public void clear(int reps)  {
        for (int i = 0; i < reps; i++) {
            if (viewStack.isEmpty()) { return; }
            viewStack.peek().removeUiElements();
            viewStack.pop();
        }
    }

    public void runViewContent(AbstractViewContent vc) {
        viewStack.push(vc);
        boolean active = true;
        while (active) {
            active = viewStack.peek().tick();
            viewStack.peek().view.wait(TICK_RATE);
        }
    }

    public AbstractViewContent getViewContent() {
        if (viewStack.isEmpty()) { return null; }
        return viewStack.peek();
    }
}
