package ldts.g0907.diplomacyfailed.game.view;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import ldts.g0907.diplomacyfailed.game.controller.PlayingController;
import ldts.g0907.diplomacyfailed.game.model.*;

import java.io.IOException;
import java.util.List;

import static com.googlecode.lanterna.input.KeyType.Character;
import static java.lang.System.currentTimeMillis;

public class LanternaPlayingView extends PlayingView{

    private final static String SKY_COLOR = "#87CEEB";
    private final static int EXPLOSION_DURATION = 30;
    private final Screen screen;
    private final int width;
    private final int height;
    private int explosion_frame_counter;
    private Position explosion_position;
    private final MusicPlayer music_player;

    public LanternaPlayingView(Screen screen, int width, int height) {
        this.screen = screen;
        this.width = width;
        this.height = height;
        this.explosion_frame_counter = 0;
        this.music_player = new MusicPlayer();
    }

    @Override
    public void draw(Battlefield battlefield){
        TextGraphics graphics = screen.newTextGraphics();
            //mind the order

            drawSky(battlefield.getWidth(), battlefield.getHeight(), graphics);
            drawTerrain(battlefield.getTerrain(), graphics);
            drawTrees(battlefield.getTrees(),graphics);
            drawPlayers(battlefield.getPlayers(), graphics);
            drawActivePlayerArrow(battlefield.getActivePlayer(), graphics);

            if(battlefield.activeShell()) drawShell(battlefield.getShell(), graphics);
            explosion:
            {
                if (battlefield.willExplode() && explosion_frame_counter == 0 ) {
                    explosion_position = battlefield.getShell().nextIntegerPosition();
                    explosion_frame_counter++;
                }
                if(explosion_frame_counter != 0) {
                    if (explosion_frame_counter == EXPLOSION_DURATION) {
                        explosion_frame_counter = 0;
                        break explosion;
                    }
                    drawExplosion(explosion_position, graphics);
                    explosion_frame_counter++;
                }
            }

        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
        screen.doResizeIfNecessary();
    }

    private void drawExplosionCenter(Position pos, TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#ec9332"));//orange
        graphics.putString(new TerminalPosition( Math.round(pos.getX()), Math.round(pos.getY())), " ");
        graphics.putString(new TerminalPosition( Math.round(pos.getX()) + 1, Math.round(pos.getY())), " ");
        graphics.putString(new TerminalPosition( Math.round(pos.getX()) -1, Math.round(pos.getY())), " ");
        graphics.putString(new TerminalPosition( Math.round(pos.getX()), Math.round(pos.getY()) - 1), " ");
        graphics.putString(new TerminalPosition( Math.round(pos.getX()), Math.round(pos.getY()) + 1), " ");

    }
    private void drawExplosionMid(Position pos, TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#eb6517"));//darker orange
        graphics.putString(new TerminalPosition( Math.round(pos.getX()) + 1, Math.round(pos.getY()) + 1), " ");
        graphics.putString(new TerminalPosition( Math.round(pos.getX()) + 1, Math.round(pos.getY()) - 1), " ");
        graphics.putString(new TerminalPosition( Math.round(pos.getX()) - 1, Math.round(pos.getY()) - 1), " ");
        graphics.putString(new TerminalPosition( Math.round(pos.getX()) - 1, Math.round(pos.getY()) + 1), " ");

    }
    private void drawExplosionOuter(Position pos, TextGraphics graphics){
        graphics.setBackgroundColor(TextColor.Factory.fromString("#eabd4e"));//yellow
        graphics.putString(new TerminalPosition( Math.round(pos.getX()) + 2, Math.round(pos.getY())), " ");
        graphics.putString(new TerminalPosition( Math.round(pos.getX()) - 2, Math.round(pos.getY())), " ");
        graphics.putString(new TerminalPosition( Math.round(pos.getX()) , Math.round(pos.getY()) - 2), " ");
        graphics.putString(new TerminalPosition( Math.round(pos.getX()) , Math.round(pos.getY()) + 2), " ");

    }
    private void drawExplosion(Position pos, TextGraphics graphics) {
        if (explosion_frame_counter < 10){
            drawExplosionCenter(pos, graphics);
            music_player.startMusic();
        }
        else if (explosion_frame_counter < 20){
            drawExplosionCenter(pos, graphics);
            drawExplosionMid(pos, graphics);
        }
        else {
            drawExplosionCenter(pos, graphics);
            drawExplosionMid(pos, graphics);
            drawExplosionOuter(pos, graphics);
        }
    }

    private void drawActivePlayerArrow(Player activePlayer, TextGraphics graphics) {
        int angle = activePlayer.getAngle();
        graphics.setBackgroundColor(TextColor.Factory.fromString(SKY_COLOR));//blue sky
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFE800")); //yellow
        int y = angle > 80 && angle < 101 ? Math.round( activePlayer.getTank().getPosition().getY()) - 7: Math.round(activePlayer.getTank().getPosition().getY())- 5;
        graphics.putString(new TerminalPosition(Math.round(activePlayer.getTank().getPosition().getX()) + 3, y), "\\/");

    }

    public void drawPlayers(List<Player> players, TextGraphics graphics) {
        //mudar para usar cores do player
//        TextColor bg_color;

        for(Player player: players){
            String color = player.getColor();
            graphics.setBackgroundColor(TextColor.Factory.fromString(SKY_COLOR));
            graphics.setForegroundColor(TextColor.Factory.fromString("#" + color));//"#" + new_color
            Tank tank = player.getTank();
            drawTankTop(tank, graphics);
            graphics.putString(new TerminalPosition(Math.round(tank.getPosition().getX()), Math.round(tank.getPosition().getY()) - 1), "/\"\"\"\"\"\\");
            graphics.putString(new TerminalPosition(Math.round(tank.getPosition().getX()), Math.round(tank.getPosition().getY())),"\\@_@_@/");
            graphics.setForegroundColor(TextColor.Factory.fromString("#ffffff")); //white
            graphics.setBackgroundColor(TextColor.Factory.fromString("#000000")); //black
            int angle = tank.getAngle();
            angle = angle > 90? 180 - angle : angle;
            graphics.putString(new TerminalPosition(Math.round(tank.getPosition().getX()) + 2, Math.round(tank.getPosition().getY()) + 1), angle + "'");
            drawPlayerName(player, graphics);
        }
    }

    public void drawPower(Position player_position, float speed) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#07CEEB"));//blue sky
        graphics.setForegroundColor(TextColor.Factory.fromString("#ffffff")); //white
        graphics.putString(new TerminalPosition(Math.round( player_position.getX()), Math.round( player_position.getY()) - 3), TerminalTextUtils.fitString(Float.toString(speed), 4));
    }


    public void drawTerrain(List<Terrain> tiles, TextGraphics graphics) {
        for(Terrain tile:tiles){
            graphics.setBackgroundColor(TextColor.Factory.fromString(tile.getColors().get(0)));
            graphics.setForegroundColor(TextColor.Factory.fromString(tile.getColors().get(1)));
            graphics.putString(new TerminalPosition(Math.round( tile.getPosition().getX()), Math.round( tile.getPosition().getY())),tile.getDecal());
        }
    }
    public void drawShell(Shell shell, TextGraphics graphics) {
        int x = Math.round( shell.getPosition().getX());
        int y = Math.round( shell.getPosition().getY());

        if (y > height - 1 || x < 0 || x > width - 1){
            shell.endShot();
        }
        //write position of shell when above the window
        else if(y < 0){
            graphics.setBackgroundColor(TextColor.Factory.fromString("#87CEEB")); //sky background
            graphics.setForegroundColor(TextColor.Factory.fromString("#f5eb08"));
            graphics.putString(new TerminalPosition(x, 0), "+" + -y);
        }
        else {
            graphics.setBackgroundColor(TextColor.Factory.fromString("#f5db00")); //yellow
            graphics.putString(new TerminalPosition(x, y), " ");
        }

    }
    public void drawPlayerName(Player player, TextGraphics graphics){
        String name = player.getName().substring(0,3);
        graphics.setBackgroundColor(TextColor.Factory.fromString(SKY_COLOR));
        graphics.setForegroundColor(TextColor.Factory.fromString("#"+player.getColor()));
        graphics.putString(new TerminalPosition(Math.round( player.getTank().getPosition().getX()) + 2, Math.round( player.getTank().getPosition().getY()) - 1), name);
    }

    public void drawTrees(List<Tree> trees, TextGraphics graphics) {
        graphics.enableModifiers(SGR.BOLD);

//           @
//          @@
//         @@@
//         @@@@
//        @@@@@@
//          |
//          ||

        for(Element tree: trees){
            graphics.setBackgroundColor(TextColor.Factory.fromString("#378805")); //green
            graphics.setForegroundColor(TextColor.Factory.fromString("#26580F")); //dark green
            graphics.putString(new TerminalPosition(Math.round( tree.getPosition().getX()), Math.round( tree.getPosition().getY()) - 6),"@");
            graphics.putString(new TerminalPosition(Math.round( tree.getPosition().getX()) - 1, Math.round( tree.getPosition().getY()) - 5),"@@@");
            graphics.putString(new TerminalPosition(Math.round( tree.getPosition().getX()) - 1, Math.round( tree.getPosition().getY()) - 4),"@@@");
            graphics.putString(new TerminalPosition(Math.round( tree.getPosition().getX()) - 2, Math.round( tree.getPosition().getY()) - 3),"@@@@@");
            graphics.putString(new TerminalPosition(Math.round( tree.getPosition().getX()) - 2, Math.round( tree.getPosition().getY()) - 2),"@@@@@");

            graphics.setBackgroundColor(TextColor.Factory.fromString("#97572b")); //brown
            graphics.setForegroundColor(TextColor.Factory.fromString("#4A3324")); //dark brown
            graphics.putString(new TerminalPosition(Math.round( tree.getPosition().getX()), Math.round( tree.getPosition().getY()) - 1),"|");
            graphics.putString(new TerminalPosition(Math.round( tree.getPosition().getX()), Math.round( tree.getPosition().getY())),"|");

        }
    }

    private void drawTankTop(Tank tank, TextGraphics graphics){
        int angle = tank.getAngle();
        if(isBetween(angle,0,9)){
        graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 2, Math.round( tank.getPosition().getY()) - 2),"/\"\"\\==D");

        }
        else if(isBetween(angle,10,20)){
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 2, Math.round( tank.getPosition().getY()) - 2),"/\"\"\\_..D");

        }
        else if(isBetween(angle,21,45)){
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 2, Math.round( tank.getPosition().getY()) - 2),"/\"\"\\_.-'D");
        }
        else if(isBetween(angle,46,60)){
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 8, Math.round( tank.getPosition().getY()) - 4),"^");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 7, Math.round( tank.getPosition().getY()) - 3),"//");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 2, Math.round( tank.getPosition().getY()) - 2),"/\"\"\\//");


        }
        else if(isBetween(angle,61,80)){
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 7, Math.round( tank.getPosition().getY()) - 4),"^");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 6, Math.round( tank.getPosition().getY()) - 3),"/'");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 2, Math.round( tank.getPosition().getY()) - 2),"/\"\"\\-`");

        }
        else if(isBetween(angle,81,90)){
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 4, Math.round( tank.getPosition().getY()) - 5),"^");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 3, Math.round( tank.getPosition().getY()) - 4),"||");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 3, Math.round( tank.getPosition().getY()) - 3),"||");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 2, Math.round( tank.getPosition().getY()) - 2),"/\"\"\\");

        }
        else if(isBetween(angle,91,100)){
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 2, Math.round( tank.getPosition().getY()) - 5),"^");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 2, Math.round( tank.getPosition().getY()) - 4),"||");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 2, Math.round( tank.getPosition().getY()) - 3),"||");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) + 1, Math.round( tank.getPosition().getY()) - 2),"/\"\"\\");

        }
        else if(isBetween(angle,101,120)){
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) - 1, Math.round( tank.getPosition().getY()) - 4),"^");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) - 1, Math.round( tank.getPosition().getY()) - 3),"'\\");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) - 1 , Math.round( tank.getPosition().getY()) - 2),"'-/\"\"\\");
        }
        else if(isBetween(angle,121,135)){
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) -2  , Math.round( tank.getPosition().getY()) - 4),"^");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) -2 , Math.round( tank.getPosition().getY()) - 3),"\\\\");
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) - 1 , Math.round( tank.getPosition().getY()) - 2),"\\\\/\"\"\\");

        }
        else if(isBetween(angle,136,160)){
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) - 4 , Math.round( tank.getPosition().getY()) - 2),"D'-._/\"\"\\");
        }
        else if(isBetween(angle,161,170)){
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) - 3 , Math.round( tank.getPosition().getY()) - 2),"D_../\"\"\\");
        }
        else if(isBetween(angle,171,180)){
            graphics.putString(new TerminalPosition(Math.round( tank.getPosition().getX()) - 2 , Math.round( tank.getPosition().getY()) - 2),"D==/\"\"\\");


        }
    }

    public void drawSky(int width, int height, TextGraphics graphics) {
        graphics.setBackgroundColor(TextColor.Factory.fromString(SKY_COLOR));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
    }

    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    @Override
    public void processInput(PlayingController controller) {
        try {
            KeyStroke key = screen.pollInput(); //readInput();
            if(key != null) createAction(key, controller);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public void startShooting(PlayingController controller){
        float SCALING_FACTOR = 0.8f;
        if (controller.activeShell()) return;
        KeyStroke key = new KeyStroke('a', false, false);
        float speed = 0;
        Position position = controller.getPowerDisplayPosition();
        drawPower(position, speed);
        double lastTime = currentTimeMillis();
        while(true){
            double current = currentTimeMillis();
            double elapsed = current - lastTime;
            try {
                key = screen.pollInput();
                screen.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(speed > 75f && speed < 100) speed += 0.01f * elapsed;
            else speed += 0.04f * elapsed;
            if (speed > 100f) speed = 0f;

            drawPower(position, speed);
            lastTime = current;
            if (key == null) continue;
            if (key.getKeyType() == Character && key.getCharacter() == 32) break;
        }
        controller.startShooting(speed * SCALING_FACTOR);
    }

    public void createAction(KeyStroke key, PlayingController controller) {
        switch (key.getKeyType()) {
            case Character -> {
                switch (key.getCharacter()) {
                    case 'w' -> controller.tiltCannonUp();
                    case 'a' -> controller.flipTankLeft();
                    case 'd' -> controller.flipTankRight();
                    case 's' -> controller.tiltCannonDown();
                    case 'p' -> controller.pauseMenu();
                    case 32 -> startShooting(controller);  //32 funciona como espaço
                    default -> {}
                }
            }
            case ArrowUp -> controller.tiltCannonUp();
            case ArrowDown -> controller.tiltCannonDown();
            case ArrowLeft -> controller.flipTankLeft();
            case ArrowRight -> controller.flipTankRight();
            case Escape -> controller.pauseMenu();
            default -> {}
        }
    }
}

