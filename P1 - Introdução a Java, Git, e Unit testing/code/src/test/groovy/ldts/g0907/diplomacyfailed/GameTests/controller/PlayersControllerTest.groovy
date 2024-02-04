package ldts.g0907.diplomacyfailed.GameTests.controller

import ldts.g0907.diplomacyfailed.game.controller.PlayersController
import ldts.g0907.diplomacyfailed.game.model.Battlefield
import ldts.g0907.diplomacyfailed.game.model.Player
import ldts.g0907.diplomacyfailed.game.model.Tank
import spock.lang.Specification

class PlayersControllerTest extends Specification {
    def players_controller
    def player
    def bf
    def pls

    def setup(){
        players_controller = new PlayersController()
        player = new Player(new Tank(0,0,45), "ABC","FFFFFF")
        pls = Arrays.asList(player)
        bf = Mock(Battlefield)
    }

    def"Tilt down"(){
        when: players_controller.tiltDown(player)
        then: player.getAngle() == 44
    }
    def"Tilt up"(){
        when: players_controller.tiltUp(player)
        then: player.getAngle() == 46
    }
    def"Flip left"(){
        when: players_controller.flipLeft(player, bf)
        then: player.getAngle() == 135
        when: players_controller.flipLeft(player, bf)
        then: player.getAngle() == 135
    }
    def"Flip right"(){
        when: player.updateAngle(90)  //updates angle to 45 + 90 = 135
        players_controller.flipRight(player, bf)
        then: player.getAngle() == 45
    }

    def"Hits"(){
        when: players_controller.indirectHit(player)
        players_controller.directHit(player)
        players_controller.directHit(player)
        players_controller.checkDeaths(pls)
        then: player.isDead()
    }


}
