package ldts.g0907.diplomacyfailed

import ldts.g0907.diplomacyfailed.game.view.LanternaPlayingView
import ldts.g0907.diplomacyfailed.menu.view.LanternaMenuView
import spock.lang.Specification

class LanternaFactoryTest extends Specification {
    def factory

    def setup(){
        factory = new LanternaFactory()
    }

    def"Creating a LanternaFactory"(){
        expect: factory.getWidth() == 100
                factory.getHeight() == 35
    }
    def"Creating Views"(){
        def mock1 = Mock(LanternaPlayingView)
        def mock2 = Mock(LanternaMenuView)
        def spy = Spy(factory)
        when: spy.createPlayingView()
        then: 1 * spy.createPlayingView() >> mock1
        when: spy.createMenuView()
        then: 1 * spy.createMenuView() >> mock2
    }
}

