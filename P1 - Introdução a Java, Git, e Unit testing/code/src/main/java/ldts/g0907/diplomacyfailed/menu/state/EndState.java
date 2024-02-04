package ldts.g0907.diplomacyfailed.menu.state;


import ldts.g0907.diplomacyfailed.ViewFactoryInterface;
import ldts.g0907.diplomacyfailed.menu.model.Menu;

public class EndState extends MenuState {
    private static String[] TEXT = {"Diplomacy Failed, Player %s won"};
//    private static String[] TEXT;//Player.getName(); //Arranjar forma de ter player a ser acedido aqui
    private static final String BUTTON1 = "PLAY AGAIN";
    private static final String BUTTON2 = "EXIT";
    private static final Menu MENU = new Menu(TEXT, BUTTON1, BUTTON2);

    public EndState(ViewFactoryInterface gui) {
        super(MENU, gui);
    }

    public EndState(ViewFactoryInterface gui, String winner) {
        super(new Menu(new String[]{"Diplomacy Failed, Player " + winner + " won"},BUTTON1,BUTTON2), gui);
        String message = "Diplomacy Failed, Player " + winner + " won";
        TEXT = new String[]{message};
    }



}
