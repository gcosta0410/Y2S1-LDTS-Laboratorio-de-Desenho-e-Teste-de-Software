package ldts.g0907.diplomacyfailed.menu.state;

import ldts.g0907.diplomacyfailed.ViewFactoryInterface;
import ldts.g0907.diplomacyfailed.menu.model.Menu;

public class MainMenuState extends MenuState{

    private static final String[] TEXT = {"Diplomacy Failed"};
    private static final String BUTTON1 = "PLAY";
    private static final String BUTTON2 = "INSTRUCTIONS";
    private static final String BUTTON3 = "EXIT";
    private static final Menu MENU = new Menu(TEXT, BUTTON1, BUTTON2, BUTTON3);

    public MainMenuState(ViewFactoryInterface gui) {
        super(MENU, gui);
    }
}
