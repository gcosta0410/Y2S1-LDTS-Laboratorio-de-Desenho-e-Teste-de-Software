package ldts.g0907.diplomacyfailed.GameTests.model

import ldts.g0907.diplomacyfailed.game.model.Entity
import ldts.g0907.diplomacyfailed.game.model.Position
import spock.lang.Shared
import spock.lang.Specification

class EntityTest extends Specification {

    @Shared entity = new Entity(0,0)

//    def setup(){
//        def entity = new Entity(0,0)
//    }
    def"Entity moveUp"(){
        expect: entity.moveUp().equals(new Position(0,-1))
    }
    def"Entity moveDown"(){
        expect: entity.moveDown().equals(new Position(0,1))
    }
    def"Entity moveLeft"(){
        expect: entity.moveLeft().equals(new Position(-1,0))
    }
    def"Entity moveRight"(){
        expect: entity.moveRight().equals(new Position(1,0))
    }
    def"Any move"() {
        expect:
        entity.move(7.9, 8.99).equals(new Position(7.9, 8.99))
    }
}
