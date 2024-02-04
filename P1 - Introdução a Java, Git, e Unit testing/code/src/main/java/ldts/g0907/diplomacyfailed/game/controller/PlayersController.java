package ldts.g0907.diplomacyfailed.game.controller;

import ldts.g0907.diplomacyfailed.game.model.Battlefield;
import ldts.g0907.diplomacyfailed.game.model.Player;
import ldts.g0907.diplomacyfailed.game.model.Tank;
import ldts.g0907.diplomacyfailed.game.model.Terrain;

import java.util.List;

import static java.lang.Math.max;
import static ldts.g0907.diplomacyfailed.game.model.Tank.MAX_HEALTH;
import static ldts.g0907.diplomacyfailed.game.model.Tank.MIN_HEALTH;


public class PlayersController {
    public void tiltUp(Player player){
        if(player.getAngle() <= 90)
            player.tiltUp();
        else
            player.tiltDown();
    }
    public void tiltDown(Player player){
        if (player.getAngle() > 90)
            player.tiltUp();
        else
            player.tiltDown();
    }
    public void flipLeft(Player player, Battlefield battlefield){
        if(player.getAngle() >= 90){
            moveLeft(player, battlefield);
        }
        else  player.flipLeft();
    }
    public void flipRight(Player player, Battlefield battlefield){
        if(player.getAngle() < 90){
            moveRight(player, battlefield);
        }
        else player.flipRight();
    }


    public void moveRight(Player player, Battlefield battlefield){
        if(player.getSteps() < Player.MAX_STEPS){
            Tank tank = player.getTank();
            if(tank.getPosition().getX() >= 95) return; //ir buscar width de outra forma
            if(canMove(player, battlefield.getTerrain(), 7)) { //7 because of tank width
                tank.setPosition(tank.move(1, -1));
                player.increaseSteps();
            }
        }
    }

    private boolean canMove(Player player, List<Terrain> terrain, int delta_x) {
        return !terrain.contains(new Terrain(player.getTank().getPosition().getX() + delta_x, player.getTank().getPosition().getY() - 1));
    }

    public void moveLeft(Player player, Battlefield battlefield){
        if(player.getSteps() < Player.MAX_STEPS) {
            Tank tank = player.getTank();
            if (tank.getPosition().getX() < 1) return;
            if (canMove(player, battlefield.getTerrain(), -1)){
                tank.setPosition(tank.move(-1, -1));
                player.increaseSteps();
        }
        }
    }
    public boolean checkDeaths(List<Player> players){
        boolean death = false;
        for (Player player:players){
            if(player.getTank().getHealth() <= 0 || player.getTank().getPosition().getY() > 50){ //get height
                player.setDead();
                death = true;
            }
        }
        return death;
    }

    public void indirectHit(Player player) {
        decreaseHealth(player, 0.34F);
        decreaseColor(player,0.30F);
    }

    public void directHit(Player player) {
        decreaseHealth(player, 0.5F);
        decreaseColor(player,0.5F);
    }

    private void decreaseColor(Player player, float dmgPercent) {
        String color = player.getColor();
        int red = Integer.parseInt(color.substring(0,2), 16);
        int green = Integer.parseInt(color.substring(2,4), 16);
        int blue = Integer.parseInt(color.substring(4,6), 16);
        red = (int) max((red * (1 - dmgPercent)), 0);
        green = (int) max((green * (1 - dmgPercent)), 0);
        blue = (int) max((blue * (1 - dmgPercent)), 0);

        String red_str = String.format("%02X", (0xFF & red));
        String green_str = String.format("%02X", (0xFF & green));
        String blue_str = String.format("%02X", (0xFF & blue));
        String new_color =  red_str + green_str + blue_str;
        player.setColor(new_color);
    }

    private void decreaseHealth(Player player,float dmgPercent) {
        Tank tank = player.getTank();

        int new_health = (int)(tank.getHealth() - MAX_HEALTH * dmgPercent);
        if(new_health < MIN_HEALTH){
            tank.setHealth(0);
        }
        else{
            tank.setHealth(new_health);
        }
    }
}
