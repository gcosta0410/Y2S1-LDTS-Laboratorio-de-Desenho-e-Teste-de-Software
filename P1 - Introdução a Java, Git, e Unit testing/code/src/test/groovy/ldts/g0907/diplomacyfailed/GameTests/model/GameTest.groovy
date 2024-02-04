package ldts.g0907.diplomacyfailed.GameTests.model

import ldts.g0907.diplomacyfailed.Game
import ldts.g0907.diplomacyfailed.LanternaFactory
import ldts.g0907.diplomacyfailed.game.state.PlayingState
import ldts.g0907.diplomacyfailed.menu.state.MenuState
import spock.lang.Specification

class GameTest extends Specification {

    def"Change State"(){
        given:
        def state = Mock(PlayingState)
        def gui = Mock(LanternaFactory)
        def menuState = Mock(MenuState)
        def game = new Game(gui)

        when: "We start a game"
        then: "It loads the menu"
        game.getState() >> menuState
        when: game.changeState(state)
        then: game.getState() == state
    }
}
