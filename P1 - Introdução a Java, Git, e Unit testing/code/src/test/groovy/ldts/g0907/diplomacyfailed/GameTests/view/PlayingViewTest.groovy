package ldts.g0907.diplomacyfailed.GameTests.view

import com.googlecode.lanterna.graphics.TextGraphics
import com.googlecode.lanterna.input.KeyStroke
import com.googlecode.lanterna.input.KeyType
import com.googlecode.lanterna.screen.Screen
import ldts.g0907.diplomacyfailed.game.controller.PlayingController
import ldts.g0907.diplomacyfailed.game.model.*
import ldts.g0907.diplomacyfailed.game.view.LanternaPlayingView
import spock.lang.Specification

class PlayingViewTest extends Specification {

    def"All drawings"(){
        given:"A playing view/drawer"
        def ss = Mock(Screen){
            newTextGraphics() >> Mock(TextGraphics)
        }
        def graphics = new LanternaPlayingView(ss, 100,50)
        def spy = Spy(graphics)
//        def tgs = Mock(TextGraphics)
        def bf = Mock(Battlefield){
            getTerrain() >> new ArrayList<Terrain>()
            getTrees() >> new ArrayList<Tree>()
            getPlayers() >> Arrays.asList(new Player(new Tank(0,0), "ABCD","FFFFFF"),new Player(new Tank(0,0), "EFGH","FFFFFF"))
            getActivePlayer() >> new Player(new Tank(0,0), "ABCD","FFFFFF")
        }

        when:
        spy.draw(bf)

        then:
        1 * spy.drawSky(_ as Integer, _ as Integer, _ as TextGraphics)
        1 * spy.drawTerrain(_ as List, _ as TextGraphics)
        1 * spy.drawTrees(_ as List,_ as TextGraphics)
        1 * spy.drawPlayers(_ as List, _ as TextGraphics)
        2 * spy.drawPlayerName(_ as Player, _ as TextGraphics)

    }

    def"Drawing a tree should print 7 layers"(){
        given:"A playing view/drawer"
        def ss = Mock(Screen)
        def graphics = new LanternaPlayingView(ss, 100,50)
        def tgS = Mock(TextGraphics)
        def bf = Mock(Battlefield){
            getTrees() >> Arrays.asList(new Tree(1F,1F))
        }
        List<Tree> trees = bf.getTrees()

        when:"Tree is drawn"
        //implicou pôr drawTrees public, testar função drawAll e testar drawTree dentro?
        graphics.drawTrees(trees, tgS)

        then:
        2 * tgS.setBackgroundColor(_)
        2 * tgS.setForegroundColor(_)
        7 * tgS.putString(*_)
    }

    def"Drawing a shell should only put a square"(){
        given:"A playing view/drawer"
        def ss = Mock(Screen)
        def graphics = new LanternaPlayingView(ss, 100,50)
        def tgS = Mock(TextGraphics)
        def bf = Mock(Battlefield){
            getShell() >>new Shell(0,0,5,45)
        }
        Shell shell = bf.getShell()
        when:"Shell is drawn"
        graphics.drawShell(shell, tgS)

        then:
        1 * tgS.setBackgroundColor(_)
        1 * tgS.putString(*_)
    }

    def"Drawing a player name"(){
        given:"A playing view/drawer"
        def ss = Mock(Screen)
        def graphics = new LanternaPlayingView(ss, 100,50)
        def tgS = Mock(TextGraphics)
        def tank = Mock(Tank){
            getPosition() >> new Position(0,0)
        }
        def player1 = new Player(tank,"ABC","FFFFFF")
        def player2 = new Player(tank,"ABCDEFG","000000")
        when:"Player1 tank is drawn"
        graphics.drawPlayerName(player1, tgS)

        then:
        1 * tgS.setForegroundColor(_)
        (_..1) * tgS.putString(*_)


        when:"Player2 tank is drawn"
        graphics.drawPlayerName(player2, tgS)

        then:
        1 * tgS.setForegroundColor(_)
        (_..1) * tgS.putString(*_)
    }
    def "Drawing terrain"(){
        given:"A playing view/drawer"
        def ss = Mock(Screen)
        def graphics = new LanternaPlayingView(ss, 100,50)
        def tgS = Mock(TextGraphics)
        def bf = Mock(Battlefield){
            getTerrain() >> Arrays.asList(new Terrain(1F,1F))
        }
        List<Terrain> tiles = bf.getTerrain()

        when:"Tree is drawn"
        graphics.drawTerrain(tiles, tgS)

        then:
        1 * tgS.setBackgroundColor(_)
        1 * tgS.setForegroundColor(_)
        1 * tgS.putString(*_)

    }

    def "Drawing the two players/tanks in the battlefield"(){
        given:"A playing view/drawer"
        def ss = Mock(Screen)
        def graphics = new LanternaPlayingView(ss, 100,50)
        def tgS = Mock(TextGraphics)
        def bf = Mock(Battlefield){
            getPlayers() >> Arrays.asList(new Player(new Tank(0,0), "ABCD","FFFFFF"),new Player(new Tank(0,0), "EFGH","FFFFFF"))
            getActivePlayer() >> new Player(new Tank(0,0), "ABCD","FFFFFF")
        }
        List<Player> players = bf.getPlayers()

        when:"Tanks are drawn"
        graphics.drawPlayers(players, tgS)

        then:
        6 * tgS.setBackgroundColor(_)
        6* tgS.setForegroundColor(_)
        (6.._) * tgS.putString(*_)

    }

    def "Drawing the sky"(){
        given:"A playing view/drawer"
        def ss = Mock(Screen)
        def graphics = new LanternaPlayingView(ss, 100,50)
        def tgS = Mock(TextGraphics)
        when:"Sky is drawn"
        //implicou pôr drawTrees public, testar função drawAll e testar drawTree dentro?
        graphics.drawSky(10,20, tgS)

        then:
        1 * tgS.setBackgroundColor(_)
        1 * tgS.fillRectangle(*_)
    }
    def "Drawing the power indicator"(){
        given:"A playing view/drawer"
        def tgS = Mock(TextGraphics)
        def ss = Mock(Screen){
            newTextGraphics() >> tgS
        }
        def graphics = new LanternaPlayingView(ss, 100,50)
        when:"The indicator is drawn"
        //implicou pôr drawTrees public, testar função drawAll e testar drawTree dentro?
        graphics.drawPower(new Position(10,20),5)

        then:
         1 * tgS.setBackgroundColor(_)
         1 * tgS.setForegroundColor(_)
         1 * tgS.putString(*_)
    }

    def"Testing actions"(){
        given:
        def key = Mock(KeyStroke)
        def ss = Mock(Screen){
            readInput() >> key
            pollInput() >> key
        }
        def graphics = new LanternaPlayingView(ss, 100,50)
        def spy = Spy(graphics)
        def ctrl = Mock(PlayingController){
            activeShell() >> true
        }
        when: key.getKeyType() >> KeyType.ArrowDown
        spy.processInput(ctrl)
        then: 1 * spy.createAction(key, ctrl)
              1 * ctrl.tiltCannonDown()

        when: key.getKeyType() >> KeyType.ArrowUp
        spy.processInput(ctrl)
        then: 1 * spy.createAction(key, ctrl)
        1 * ctrl.tiltCannonUp()

        when: key.getKeyType() >> KeyType.ArrowRight
        spy.processInput(ctrl)
        then: 1 * spy.createAction(key, ctrl)
        1 * ctrl.flipTankRight()

        when: key.getKeyType() >> KeyType.ArrowLeft
        spy.processInput(ctrl)
        then: 1 * spy.createAction(key, ctrl)
        1 * ctrl.flipTankLeft()

        when: key.getKeyType() >> KeyType.Escape
        spy.processInput(ctrl)
        then: 1 * spy.createAction(key, ctrl)
        1 * ctrl.pauseMenu()

        when: key.getKeyType() >> KeyType.Character
             key.getCharacter() >> 'w'
        spy.processInput(ctrl)
        then: 1 * spy.createAction(key, ctrl)
        1 * ctrl.tiltCannonUp()

        when: key.getKeyType() >> KeyType.Character
        key.getCharacter() >> 's'
        spy.processInput(ctrl)
        then: 1 * spy.createAction(key, ctrl)
        1 * ctrl.tiltCannonDown()

        when: key.getKeyType() >> KeyType.Character
        key.getCharacter() >> 'a'
        spy.processInput(ctrl)
        then: 1 * spy.createAction(key, ctrl)
        1 * ctrl.flipTankLeft()

        when: key.getKeyType() >> KeyType.Character
        key.getCharacter() >> 'd'
        spy.processInput(ctrl)
        then: 1 * spy.createAction(key, ctrl)
        1 * ctrl.flipTankRight()

        when: key.getKeyType() >> KeyType.Character
        key.getCharacter() >> 'p'
        spy.processInput(ctrl)
        then: 1 * spy.createAction(key, ctrl)
        1 * ctrl.pauseMenu()

        when: key.getKeyType() >> KeyType.Character
        key.getCharacter() >>  32
        spy.processInput(ctrl)
        then: 1 * spy.createAction(key, ctrl)
        1 * spy.startShooting(ctrl)





    }
}