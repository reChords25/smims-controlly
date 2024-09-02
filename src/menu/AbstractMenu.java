package menu;

import controller.AbstractController;
import sas.View;

public abstract class AbstractMenu {

    protected View view;
    protected AbstractController controller;

    public AbstractMenu(View view, AbstractController controller) {
        this.view = view;
        this.controller = controller;
        initView();
    }

    protected abstract void initView();

    protected abstract void run();
}
