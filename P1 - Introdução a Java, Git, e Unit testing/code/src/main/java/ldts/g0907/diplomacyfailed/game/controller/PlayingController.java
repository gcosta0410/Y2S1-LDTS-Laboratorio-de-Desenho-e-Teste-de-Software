package ldts.g0907.diplomacyfailed.game.controller;

import ldts.g0907.diplomacyfailed.game.model.*;

import java.util.List;

public class PlayingController {
    private Battlefield battlefield;
    private PlayersController players_controller;
    private ShellController shell_controller;

    private float last_int_x;
    private float last_int_y;
    private boolean pause;
    private boolean collided;

    public boolean update(Battlefield battlefield){
        if(collided){
            battlefield.eraseShell();
            collided = false;
        }

        if (battlefield.activeShell()) {
            Shell shell = battlefield.getShell();
            shell_controller.nextPosition(shell);
            //to only check for collisions every new terminal tile the Shell touches
            if (!shell.getPosition().sameTile(new Position(last_int_x, last_int_y))) {
                collided = checkTankCollisions(battlefield) || checkTerrainCollisions(battlefield);
                last_int_x = Math.round(shell.getPosition().getX());
                last_int_y = Math.round(shell.getPosition().getY());
            }
            shell_controller.checkShellPosition(battlefield, shell);
        }

        if(battlefield.shouldSwitchTurn()) battlefield.endTurn();

        return players_controller.checkDeaths(battlefield.getPlayers());
    }

    private boolean checkTerrainCollisions(Battlefield battlefield) {
        List<Terrain> tiles = battlefield.getTerrain();
        Shell shell = battlefield.getShell();
        float x = Math.round(shell.getPosition().getX());
        float y = Math.round(shell.getPosition().getY());

        if(tiles.contains(new Terrain(x, y))){
            battlefield.setWillExplode(true);
            battlefield.explode(x,y);
            return true;
        }
        return false;
    }

    public PlayingController(Battlefield battlefield){
        this.battlefield = battlefield;
        players_controller = new PlayersController();
        shell_controller =new ShellController();
        last_int_x = -1;
        last_int_y = -1;
        collided = false;
        pause = false;
    }
    public void tiltCannonDown(){
        players_controller.tiltDown(battlefield.getActivePlayer());
    }
    public void tiltCannonUp(){
        players_controller.tiltUp(battlefield.getActivePlayer());
    }
    public void flipTankLeft(){
        players_controller.flipLeft(battlefield.getActivePlayer(), battlefield);
        battlefield.fallToFloor();
    }
    public void flipTankRight(){
        players_controller.flipRight(battlefield.getActivePlayer(), battlefield);
        battlefield.fallToFloor();
    }

    public void startShooting(float speed){
        last_int_x = -1;
        last_int_y = -1;
        battlefield.shoot(speed);
        battlefield.prepareNextTurn();
    }

    public Position getPowerDisplayPosition() {
        return battlefield.getActivePlayer().getTank().getCannonPosition();
    }

    public boolean activeShell() {
        return battlefield.activeShell();
    }

    public boolean getPause() {
        return pause;
    }
    public void setPause(boolean b){
        pause = b;
    }
    public void pauseMenu() {
        pause = true;
    }

    public boolean checkTankCollisions(Battlefield battlefield) {
        Position shell_pos = battlefield.getShell().getPosition();
        List<Terrain> tiles = battlefield.getTerrain();
        for(Player player:battlefield.getPlayers()){
            Position tank_pos = player.getTank().getPosition();
            float tank_x = tank_pos.getX();
            float tank_y = tank_pos.getY();

            if(shell_pos.sameTile(tank_pos)
                    || shell_pos.sameTile(tank_x + 1, tank_y + 0)
                    || shell_pos.sameTile(tank_x + 2, tank_y + 0)
                    || shell_pos.sameTile(tank_x + 3, tank_y + 0)
                    || shell_pos.sameTile(tank_x + 4, tank_y + 0)
                    || shell_pos.sameTile(tank_x + 5, tank_y + 0)
                    || shell_pos.sameTile(tank_x + 6, tank_y + 0)

                    || shell_pos.sameTile(tank_x + 0, tank_y - 1)
                    || shell_pos.sameTile(tank_x + 1, tank_y - 1)
                    || shell_pos.sameTile(tank_x + 2, tank_y - 1)
                    || shell_pos.sameTile(tank_x + 5, tank_y - 1)
                    || shell_pos.sameTile(tank_x + 6, tank_y - 1)

                    || shell_pos.sameTile(tank_x + 1, tank_y - 2)
                    || shell_pos.sameTile(tank_x + 2, tank_y - 2)
                    || shell_pos.sameTile(tank_x + 3, tank_y - 2)
                    || shell_pos.sameTile(tank_x + 4, tank_y - 2)
                    || shell_pos.sameTile(tank_x + 5, tank_y - 2)
            )
            {
                battlefield.setWillExplode(true);
                battlefield.explode(shell_pos.getX(),shell_pos.getY());
                players_controller.directHit(player);
                return true;
            }
            else if(tiles.contains(new Terrain(Math.round(shell_pos.getX()), Math.round(shell_pos.getY()))) &&
                    (shell_pos.sameTile(tank_x - 1, tank_y + 0)
                            || shell_pos.sameTile(tank_x + 7, tank_y + 0)
                            || shell_pos.sameTile(tank_x + -1, tank_y + 0)
                            || shell_pos.sameTile(tank_x + 7, tank_y - 1)
                            || shell_pos.sameTile(tank_x + -1, tank_y - 1 )
                            || shell_pos.sameTile(tank_x + 7, tank_y - 2)
                            || shell_pos.sameTile(tank_x + -1, tank_y - 2 )
                            || shell_pos.sameTile(tank_x + 7, tank_y + 1)
                            || shell_pos.sameTile(tank_x + -1, tank_y + 1 )

                            || shell_pos.sameTile(tank_x + -2, tank_y + 1 )
                            || shell_pos.sameTile(tank_x + 8, tank_y + 1)
                            || shell_pos.sameTile(tank_x + -2, tank_y + 0 )
                            || shell_pos.sameTile(tank_x + 8, tank_y + 0)
                            || shell_pos.sameTile(tank_x + -2, tank_y -1 )
                            || shell_pos.sameTile(tank_x + 8, tank_y -1)
                            || shell_pos.sameTile(tank_x + -2, tank_y -2 )
                            || shell_pos.sameTile(tank_x + 8, tank_y -2)
                    )
            ){
                battlefield.setWillExplode(true);
                battlefield.explode(shell_pos.getX(),shell_pos.getY());
                players_controller.indirectHit(player);
                return true;
            }
        }
        return false;
    }

    public String getWinner(Battlefield battlefield) {
        for(Player player: battlefield.getPlayers()){
            if(!player.isDead())
                return player.getName() ;
        }
        return "";
    }
}

