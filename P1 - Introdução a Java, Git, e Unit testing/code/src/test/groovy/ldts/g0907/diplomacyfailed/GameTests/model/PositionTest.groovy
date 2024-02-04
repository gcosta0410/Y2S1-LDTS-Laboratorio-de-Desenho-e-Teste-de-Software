package ldts.g0907.diplomacyfailed.GameTests.model

import ldts.g0907.diplomacyfailed.game.model.Position
import spock.lang.Specification

class PositionTest extends Specification  {

        def "Two positions are the same if very close"(){
            given:
            Position pos1 = new Position(1.999999999999999999998,0.0)
            Position pos2 = new Position(1.999999999999999999999,0.0)

            expect:
            pos1.equals(pos2)
            pos1.sameTile(pos2)
            pos1.sameTile(2.4,0.4)
            pos1.sameTile(1.5,-0.5)
        }
        def "Two positions are not the same if not close enough"(){
            given:
            Position pos1 = new Position(1.9999998,0.0)
            Position pos2 = new Position(1.9999999,0.0)

            expect:
            !pos1.equals(pos2)
            pos1.sameTile(pos2)
        }

    }

