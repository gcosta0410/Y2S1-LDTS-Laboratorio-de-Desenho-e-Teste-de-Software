package ldts.g0907.diplomacyfailed.menu.state;

import ldts.g0907.diplomacyfailed.ViewFactoryInterface;
import ldts.g0907.diplomacyfailed.menu.model.Menu;

public class InstructionsState extends MenuState{

    private static final String[] TEXT = {"                                          The game:",
            "","",
            "Diplomacy has failed! Two players have resorted to forceful methods to get what they want.",
            "Each one has a tank whose cannon can be controlled up or down using the W and S keys.",
            "The tank can also be flipped to face the other way using the A or D keys.",
            "When ready to shoot, pressing the spacebar once will lock the angle and start to build up force. ",
            "Pressing spacebar again will shoot with the desired angle and force.",
            "Hit someone else directly 2 times and they're dead.",
            "Alternatively you can also kill them using shrapnel from shooting near them enough times.",
            "","","",
            "                   Aim up",
            "                   -----",
            "                   | W |",
            "                   -----                         Click once to lock angle",
            "           -----   -----   -----                      -------------",
            " Turn left | A |   | S |   | D |  Turn right          |   [___]   |",
            "           -----   -----   -----                      -------------",
            "                  Aim Down                         Click again to shoot",
            };
    private static final String BUTTON1 = "PLAY";
    private static final String BUTTON2 = "EXIT";
    private static final Menu MENU = new Menu(TEXT, BUTTON1, BUTTON2);

    public InstructionsState(ViewFactoryInterface gui) {
        super(MENU, gui);
    }


}
