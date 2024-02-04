package ldts.g0907.diplomacyfailed

import ldts.g0907.diplomacyfailed.menu.state.EndState
import spock.lang.Specification

class StateTest extends Specification {

    def"Changing the observer of the state"(){
        def gui = Mock(LanternaFactory)
        def observer = Mock(Game)
        def state = new EndState(gui)
        when:"State is changed"
        state.setObserver(observer)
        then:
        state.getObserver() == observer

    }
}


