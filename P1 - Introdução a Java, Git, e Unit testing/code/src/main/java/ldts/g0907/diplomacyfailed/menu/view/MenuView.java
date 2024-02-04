package ldts.g0907.diplomacyfailed.menu.view;

import ldts.g0907.diplomacyfailed.menu.controller.MenuController;
import ldts.g0907.diplomacyfailed.menu.controller.VoidController;
import ldts.g0907.diplomacyfailed.menu.model.Menu;

//needed to change from interface to abstract class because of interface variables being final
public abstract class MenuView {

    MenuController menuController = new VoidController();

    public abstract void draw(Menu menu);
    public abstract void processInput();
    public MenuController getMenuController(){return menuController;}
}
