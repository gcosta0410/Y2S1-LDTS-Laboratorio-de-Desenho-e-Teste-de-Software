package ldts.g0907.diplomacyfailed.GameTests.controller

import ldts.g0907.diplomacyfailed.game.controller.ShellController
import ldts.g0907.diplomacyfailed.game.model.Battlefield
import ldts.g0907.diplomacyfailed.game.model.Position
import ldts.g0907.diplomacyfailed.game.model.Shell
import spock.lang.Specification

class ShellControllerTest extends Specification {
    def"Main test"(){
        def ctrl = new ShellController()
        def spy = Spy(ctrl)
        def bf = new Battlefield(100, 50)
        def shell = Mock(Shell){
            getPosition() >> new Position(10,10)
        }
        when: spy.nextPosition(shell)
        then: 1 * shell.nextPosition()
              1* shell.setPosition(_)
        when: spy.nextIntegerPosition(shell)
        then: 1 * shell.nextIntegerPosition()
              1 * shell.setPosition(_)
        when: spy.checkShellPosition(bf, shell)
        then: 0 * bf.eraseShell()
    }
}
