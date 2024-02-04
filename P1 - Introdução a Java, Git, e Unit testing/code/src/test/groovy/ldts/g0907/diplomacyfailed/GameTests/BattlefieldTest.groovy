package ldts.g0907.diplomacyfailed.GameTests

import ldts.g0907.diplomacyfailed.game.model.Battlefield
import ldts.g0907.diplomacyfailed.game.model.Position
import ldts.g0907.diplomacyfailed.game.model.Terrain
import ldts.g0907.diplomacyfailed.game.model.Tree
import spock.lang.Specification

class BattlefieldTest extends Specification {

    //não dá para fazer counts no construtor?
//    def"creating a Battlefield"(){
//        given: "A new Battlefield"
//            URL url = getClass().getResource("map1.txt")
//            String filename = url.getPath()
//            Battlefield bf = Spy(Battlefield, global:true)
//            1 * bf.readBattlefield(*_)
//    }

    def"An entity in a battlefield falls until it reaches the floor"(){
        given:"An Entity"
        Tree tree = new Tree(10,28)
        URL resource = getClass().getClassLoader().getResource("map1.txt")
        String filename = resource.path
        Battlefield bf = new Battlefield(100,50, filename)
        bf.addTree(tree)
        when:"Some terrain is destroyed"
        bf.destroy(tree.getPosition().getX() ,(float) (tree.getPosition().getY() + 1F) )
        then:"The entity falls to the floor"
        tree.getPosition().roughlyEquals(new Position(10,29))
    }

    def"Destroying a terrain tile should remove it"(){
        given:
        def bf = new Battlefield(10,10)
        bf.addTile(new Terrain(0,0))
        when:"Tile is destroyed"
        bf.destroy(0,0)
        then: bf.getTerrain().size() == 0

        when:"Another tile is added but we try to destroy an invalid tile"
        bf.addTile(new Terrain(0,0))
        bf.addTile(new Terrain(0,1))
        bf.destroy(1,0)
        then: "Nothing changes"
        bf.getTerrain().size() == 2
    }
    def"Battlefield flow"(){
        def bf = new Battlefield(100,50)
        expect: !bf.willExplode()
                !bf.activeShell()
                !bf.shouldSwitchTurn()
        when: bf.setWillExplode(true)
        then: bf.willExplode()
        when: bf.prepareNextTurn()
            bf.eraseShell()
        then: bf.shouldSwitchTurn()
    }
}
