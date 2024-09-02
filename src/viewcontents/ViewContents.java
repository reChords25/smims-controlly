package viewcontents;

import controller.AbstractController;
import sas.View;

public class ViewContents {

    private static ViewContents instance;

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

    public void setViewContent(AbstractViewContent vc) {
        vc.run();
    }
}
