package minigames;

import controller.AbstractController;
import sas.View;

public abstract class AbstractGame {
    
    public static final String PATH_TO_RESOURCES = "resources/";

    protected AbstractController controller;
    protected View view;

    public AbstractGame(AbstractController controller, View view){
        this.controller = controller;
        this.view = view;
        initView();
    }

    protected abstract void initView();

    public abstract void runGame();

}
