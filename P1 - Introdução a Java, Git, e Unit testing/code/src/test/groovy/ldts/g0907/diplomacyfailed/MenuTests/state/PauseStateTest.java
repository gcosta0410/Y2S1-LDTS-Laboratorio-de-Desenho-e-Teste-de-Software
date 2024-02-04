package ldts.g0907.diplomacyfailed.MenuTests.state;

import ldts.g0907.diplomacyfailed.Game;
import ldts.g0907.diplomacyfailed.ViewFactoryInterface;
import ldts.g0907.diplomacyfailed.game.state.PlayingState;
import ldts.g0907.diplomacyfailed.menu.state.PauseState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PauseStateTest {

    private Game game;
    private PauseState pauseState;
    private ViewFactoryInterface gui;
    private PlayingState playingState;

    @BeforeEach
    public void setup(){
        game = Mockito.mock(Game.class);
        playingState = Mockito.mock(PlayingState.class);
        gui = Mockito.mock(ViewFactoryInterface.class);
        pauseState = new PauseState(gui, playingState);
    }

    @Test
    public void testReturnToGame(){
        pauseState.setObserver(game);
        pauseState.returnToGame();
        Mockito.verify(game, Mockito.times(1)).changeState(playingState);
    }


}
