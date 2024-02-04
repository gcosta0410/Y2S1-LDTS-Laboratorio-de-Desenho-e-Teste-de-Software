package ldts.g0907.diplomacyfailed.GameTests.model

import ldts.g0907.diplomacyfailed.game.model.Element
import ldts.g0907.diplomacyfailed.game.model.Position
import spock.lang.Specification

class ElementTest extends Specification{
    def"Creating an Element"(){
        given:
        Position pos = new Position(7,7)
        Element element = new Element(pos)
        expect: element.getPosition().equals(new Position(7,7))
    }
}
