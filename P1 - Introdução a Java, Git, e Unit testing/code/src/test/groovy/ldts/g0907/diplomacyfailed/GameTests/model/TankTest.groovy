package ldts.g0907.diplomacyfailed.GameTests.model


import ldts.g0907.diplomacyfailed.game.model.Tank
import spock.lang.Specification

class TankTest extends Specification {


    def "Updating angle"(){
        given:"A tank"
        def tank = new Tank(0,0)
        when:"Angle is changed"
        tank.updateAngle(172)
        then: tank.getAngle() == 172
        when:"Angle exceeds 180"
        tank.updateAngle(185)
        then:tank.getAngle() == 180
        when:"Angle is less than 0"
        tank.updateAngle(-2847)
        then:tank.getAngle() == 0
    }
}
