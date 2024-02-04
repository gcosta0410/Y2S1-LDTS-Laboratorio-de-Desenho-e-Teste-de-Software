package ldts.g0907.diplomacyfailed.menu.state;

import ldts.g0907.diplomacyfailed.ViewFactoryInterface;
import ldts.g0907.diplomacyfailed.game.state.PlayingState;
import ldts.g0907.diplomacyfailed.menu.controller.MenuController;
import ldts.g0907.diplomacyfailed.menu.model.Menu;

public class PauseState extends MenuState{

    private static final String[] TEXT = {"PAUSED"};
    private static final String BUTTON1 = "CONTINUE";
    private static final String BUTTON2 = "EXIT";
    private static final Menu MENU = new Menu(TEXT, BUTTON1, BUTTON2);
    private PlayingState playing_state;

    public PauseState(ViewFactoryInterface gui, PlayingState playing_state) {
        super(MENU, gui);
        this.playing_state = playing_state;
    }

    public void returnToGame(){
        observer.changeState(playing_state);
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
            case "CONTINUE" -> returnToGame();
            case "Map 1" -> observer.changeState(new PlayingState(gui, "map1"));
            case "Random" -> observer.changeState(new PlayingState(gui, "randomMap"));
            case "EXIT" -> System.exit(0);
            default -> {
            }
        }
    }
}
