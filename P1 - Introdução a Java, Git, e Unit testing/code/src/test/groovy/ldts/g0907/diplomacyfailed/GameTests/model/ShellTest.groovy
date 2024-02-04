package ldts.g0907.diplomacyfailed.GameTests.model

import ldts.g0907.diplomacyfailed.game.model.Position
import ldts.g0907.diplomacyfailed.game.model.Shell
import spock.lang.Specification

class ShellTest extends Specification {



    def"The next position of a moving shell"(){
        given:"A shell"
        Shell shell = new Shell(0f,10f,5,-45)

        expect: shell.nextPosition().roughlyEquals(0.017677f,10.0179f ) // bigger y, means it is falling
        when: shell = new Shell(0,0,0,45)
        then: shell.nextPosition().roughlyEquals(0,0 )
        when: shell = new Shell(0,0,10,90)
        then: shell.nextPosition().roughlyEquals(0,-0.04999 )
    }

    def"The next different position in the terminal of a moving shell"(){
        given:"A shell"
        Shell shell = new Shell(0f,10f,5,-45)
        expect: shell.nextIntegerPosition().roughlyEquals(0f,11f)
    }

    def"Creating a shell"(){
        given: def shell = new Shell(new Position(10,10), 10, 10)
        expect: shell.isActive()
                Math.abs(shell.getX_speed() - 9.848) < 0.001
                Math.abs(shell.getY_speed() - 1.736) < 0.001
        when: shell.endShot()
        then: !shell.isActive()
    }

}
