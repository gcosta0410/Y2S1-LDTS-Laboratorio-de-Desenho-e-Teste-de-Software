package ldts.g0907.diplomacyfailed.game.controller;

import ldts.g0907.diplomacyfailed.game.model.Battlefield;
import ldts.g0907.diplomacyfailed.game.model.Shell;

public class ShellController {

    public void nextPosition(Shell shell){
        shell.setPosition(shell.nextPosition());
    }
    public void nextIntegerPosition(Shell shell){
        shell.setPosition(shell.nextIntegerPosition());
    }

    public void checkShellPosition(Battlefield battlefield, Shell shell){
        if (battlefield.outOfBounds(shell)) battlefield.eraseShell();
    }


}
