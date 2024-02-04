package ldts.g0907.diplomacyfailed.menu.controller;

import ldts.g0907.diplomacyfailed.menu.model.Menu;

public class ChangeOptionUpController implements MenuController{

    @Override
    public String executeAction(Menu menu) {
        menu.changeOptionUp();
        return "";
    }
}
