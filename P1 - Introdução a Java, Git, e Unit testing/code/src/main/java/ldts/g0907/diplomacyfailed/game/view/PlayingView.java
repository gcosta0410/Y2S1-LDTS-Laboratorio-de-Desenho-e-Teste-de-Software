package ldts.g0907.diplomacyfailed.game.view;

import ldts.g0907.diplomacyfailed.game.controller.PlayingController;
import ldts.g0907.diplomacyfailed.game.model.Battlefield;


public abstract class PlayingView {

    public abstract void draw(Battlefield battlefield);
    public abstract void processInput(PlayingController controller);

}
