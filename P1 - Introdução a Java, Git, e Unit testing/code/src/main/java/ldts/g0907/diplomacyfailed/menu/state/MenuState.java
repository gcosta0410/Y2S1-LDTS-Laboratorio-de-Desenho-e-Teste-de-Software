package ldts.g0907.diplomacyfailed.menu.state;

import ldts.g0907.diplomacyfailed.State;
import ldts.g0907.diplomacyfailed.ViewFactoryInterface;
import ldts.g0907.diplomacyfailed.game.state.PlayingState;
import ldts.g0907.diplomacyfailed.menu.controller.MenuController;
import ldts.g0907.diplomacyfailed.menu.model.Menu;
import ldts.g0907.diplomacyfailed.menu.view.MenuView;

public abstract class MenuState extends State {

    protected Menu menu;
    protected MenuView menuView;

    public MenuState(Menu menu, ViewFactoryInterface gui) {
        super(gui);
        this.menu = menu;
        menuView = gui.createMenuView();
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public MenuView getMenuView() {
        return menuView;
    }

    public void setMenuView(MenuView menuView) {
        this.menuView = menuView;
    }

    @Override
    public void processInput() {
        menuView.processInput();
    }

    @Override
    public void draw() {
        menuView.draw(menu);
    }

    @Override
    public void update(){
        MenuController menuController = menuView.getMenuController();
        String action = menuController.executeAction(menu);

        if(!action.equals("")) {
            StringBuilder sb = new StringBuilder(action);
            sb.deleteCharAt(0);
            action = sb.toString();
        }

        switch (action) {
            case "PLAY", "PLAY AGAIN" -> observer.changeState(new ChooseMapState(gui));
            case "INSTRUCTIONS" -> observer.changeState(new InstructionsState(gui));
            case "Map 1" -> observer.changeState(new PlayingState(gui, "map1"));
            case "Random" -> observer.changeState(new PlayingState(gui, "randomMap"));
            case "EXIT" -> System.exit(0);
            default -> {
            }
        }
    }
}

