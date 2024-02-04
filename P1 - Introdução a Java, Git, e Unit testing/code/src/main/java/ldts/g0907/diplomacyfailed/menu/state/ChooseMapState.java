package ldts.g0907.diplomacyfailed.menu.state;

import ldts.g0907.diplomacyfailed.ViewFactoryInterface;
import ldts.g0907.diplomacyfailed.menu.model.Menu;

public class ChooseMapState extends MenuState {

    private static final String[] TEXT = {"Choose a map"};
    private static final String BUTTON1 = "Map 1";
    private static final String BUTTON2 = "Random";
    private static final String BUTTON3 = "EXIT";
    private static final Menu MENU = new Menu(TEXT, BUTTON1, BUTTON2, BUTTON3);

    public ChooseMapState(ViewFactoryInterface gui) {
        super(MENU, gui);
    }
}
