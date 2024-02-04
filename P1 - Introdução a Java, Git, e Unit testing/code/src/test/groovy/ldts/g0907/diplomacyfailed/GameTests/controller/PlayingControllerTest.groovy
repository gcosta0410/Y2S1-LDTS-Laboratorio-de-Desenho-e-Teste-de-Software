package ldts.g0907.diplomacyfailed.GameTests.controller

import ldts.g0907.diplomacyfailed.game.controller.PlayersController
import ldts.g0907.diplomacyfailed.game.controller.PlayingController
import ldts.g0907.diplomacyfailed.game.model.Battlefield
import ldts.g0907.diplomacyfailed.game.model.Player
import ldts.g0907.diplomacyfailed.game.model.Tank
import ldts.g0907.diplomacyfailed.game.model.Terrain
import spock.lang.Specification

class PlayingControllerTest extends Specification{
    def ctrl
    def bf
    def playersCtrl
    def setup(){
        playersCtrl = Mock(PlayersController)
        bf = Mock(Battlefield){
            getActivePlayer() >> new Player(new Tank(0,0), "ABC", "FFFFFF")
            getTerrain() >> Arrays.asList(new Terrain(1,1))
        }
        ctrl = new PlayingController(bf)
    }

    def"Start shooting"(){
        when: ctrl.startShooting(10)
        then: 1 * bf.shoot(10)
              1 * bf.prepareNextTurn()
    }
    def"Pause"(){
        when: ctrl.pauseMenu()
        then: ctrl.getPause()
    }

    def"Movement"(){
        when: ctrl.flipTankRight()
        then: 1 * bf.fallToFloor()
        when: ctrl.flipTankLeft()
        then: 1 * bf.fallToFloor()
    }
}
