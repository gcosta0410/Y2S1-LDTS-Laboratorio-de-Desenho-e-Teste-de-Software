package ldts.g0907.diplomacyfailed.GameTests.model

import ldts.g0907.diplomacyfailed.game.model.Terrain
import spock.lang.Specification

class TerrainTest extends Specification {
    def"Two tiles are the same if in the same position and with the same drawing"(){
        given:"Two tiles"
        Terrain tile1 = new Terrain(1,1)
        tile1.setDecal("a")
        Terrain tile2 = new Terrain(1,1)
        tile2.setDecal("a")

        and:
        Terrain tile3 = new Terrain(1,1)
        tile3.setDecal("a")
        Terrain tile4 = new Terrain(1,1)
        tile4.setDecal("v")

        and:
        Terrain tile5 = new Terrain(1f, 1.02f)
        tile5.setDecal("a")
        Terrain tile6 = new Terrain(1,1)
        tile6.setDecal("a")

        expect: tile1.equals(tile2)
                !tile3.equals(tile4)
                !tile5.equals(tile6)
    }
}
