package ldts.g0907.diplomacyfailed.GameTests.model

import ldts.g0907.diplomacyfailed.game.model.Player
import ldts.g0907.diplomacyfailed.game.model.Tank
import spock.lang.Specification

class PlayerTest extends Specification{

    def name
    def player
    def tank

    def setup() {
        name = "Player 1"
        tank = new Tank(0,0)
        player = new Player(tank, name,"FF0000")
    }


    def"creating a Player1"(){
        given: "A new Player1"

        when:"Instanced getTank()"
            Tank tank1 = player.getTank()
        then:
            tank1 == tank

        when: "Instanced getName()"
            String name1 = player.getName()
        then:
            name1 == name

        when: "Instanced isDead()"
            boolean result = player.isDead()
        then:
            !result

        when: "Instanced getName()"
            player.setColor("000000")
            String color = player.getColor()
        then:
            color == "000000"
        when:
            String og_color = player.getOriginalColor()
        then:
            og_color == "FF0000"
        expect:
            player.getSteps() == 0
        when:
            player.increaseSteps()
        then:
            player.getSteps() == 1
        when:
            player.resetSteps()
        then:
            player.getSteps() == 0
    }

    def"altering a Player 1"(){
        given: "An existing Player1"
        when: "Instanced setName()"
            String name2 = "Player 2"
            player.setName(name2)
        then:
            name2 == player.getName()

        when: "Instanced setDead()"
            player.setDead()
        then:
            player.isDead() == true
    }
}
