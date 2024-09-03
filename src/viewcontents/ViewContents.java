package viewcontents;

import controller.AbstractController;
import sas.View;

public class ViewContents {

    private static ViewContents instance;
    public static final int TICK_RATE = 20;

    private AbstractViewContent vc;

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
        instance = new ViewContents(view, controller);
    }

    public ViewContents(View view, AbstractController controller) {
        
    }

    public void clear()  {
        if (vc == null) return;
        vc.removeUiElements();
    }

    public void setViewContent(AbstractViewContent vc) {
        this.vc = vc;
        boolean active = true;
        while (active) {
            active = vc.run();
            vc.view.wait(TICK_RATE);
        }
    }
}
