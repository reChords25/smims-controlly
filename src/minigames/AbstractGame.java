package minigames;

import controller.AbstractController;
import sas.View;

public abstract class AbstractGame {

    /* Static Variables */
    public static final String PATH_TO_RESOURCES = "resources/";

    /* Static Methods */

    /* Object Variables */
    protected AbstractController controller;
    protected View view;

    /* Constructors */
    public AbstractGame(AbstractController controller, View view){
        this.controller = controller;
        this.view = view;
        initView();
    }


    /* Object Methods */
    protected abstract void initView();

    public abstract void runGame();

    /* Getters and Setters */

    /* Inner Classes */

}
