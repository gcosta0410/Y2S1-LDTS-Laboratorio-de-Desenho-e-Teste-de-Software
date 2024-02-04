package ldts.g0907.diplomacyfailed.game.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Battlefield {

    private final int width;
    private final int height;
    private int player_counter;
    private List<Tree> trees;
    private List<Terrain> tiles;
    private List<Player> players;
    private boolean preparing;
    private boolean will_explode;

    //    private List<Tank> tanks; //there will only be two tanks;
    private Shell shell; //só deve haver uma shell de cada vez?


    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public List<Terrain> getTerrain() {
        return tiles;
    }
    public Shell getShell() {
        return shell;
    }

    public Battlefield(int width, int height, String filename) {
        this.width = width;
        this.height = height;
        this.player_counter = 0;
        this.preparing = false;
        this.will_explode = false;
        readBattlefield(filename);
    }
    public Battlefield(int width, int height) {
        this.width = width;
        this.height = height;
        this.player_counter = 0;
        trees = new ArrayList<>();
        tiles = new ArrayList<>();
        players = new ArrayList<>();
        this.preparing = false;
        this.will_explode = false;
    }

    public void readBattlefield(String filename) {
        List<String> auxTerrainList = new ArrayList<>();
        List<String> auxTreesList = new ArrayList<>();
        List<String> auxTanksList = new ArrayList<>();
        trees = new ArrayList<>();
        tiles = new ArrayList<>();
        players = new ArrayList<>();
        try {
            File battlefield = new File(filename);
            Scanner myReader = new Scanner(battlefield);
            int row = 1;
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (row == 1) {
                    auxTerrainList = Arrays.asList(line.split(","));
                }
                if (row == 2){
                    auxTreesList = Arrays.asList(line.split(";"));
                }
                if(row == 3)
                    auxTanksList = Arrays.asList(line.split(";"));
                row++;
            }
            addFileTrees(auxTreesList);
            addFileTanks(auxTanksList);
            addFileTerrain(auxTerrainList);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private void addFileTerrain(List<String> auxTerrainList) {
        List<Integer> terrainList = new ArrayList<>();
        for(String number: auxTerrainList){
            terrainList.add(parseInt(number));
        }
        terrainList = terrainList.stream().map(number -> height - number ).collect(Collectors.toList());
        int i = 0;
        for(int altitude: terrainList){
            while(altitude < height){
                tiles.add(new Terrain(i, altitude));
                altitude++;
            }
            i++;
        }
    }

    //Pass players name
    private void addFileTanks(List<String> auxTanksList) {
        int tank_counter = 0;
        for (String tuple: auxTanksList){
            int angle = tank_counter == 0 ? 0:180;
            String color = tank_counter == 0 ? "FF0000":"0000FF";
            tuple = tuple.replaceAll("[()]","");
            List<String> pair = Arrays.asList(tuple.split(","));
            String name = tank_counter == 0 ? "PL1" : "PL2";
            players.add(new Player(new Tank((float) (parseInt(pair.get(0))),height - parseInt(pair.get(1)), angle),name, color ));
            tank_counter++;
        }
    }

    private void addFileTrees(List<String> auxTreesList) {
        for (String tuple: auxTreesList){
            tuple = tuple.replaceAll("[()]","");
            List<String> pair = Arrays.asList(tuple.split(","));
            trees.add(new Tree((float) (parseInt(pair.get(0))),height - parseInt(pair.get(1))));
        }
    }

    public List<Tree> getTrees() {
        return trees;
    }
    public void addTree(Tree tree){
        trees.add(tree);
    }
    public void addTile(Terrain tile){
        tiles.add(tile);
    }
    public void destroy(float x, float y){
        int idx = 0;
        boolean found = false;
        while (idx < tiles.size()){
            if(tiles.get(idx).getPosition().roughlyEquals(new Position( x, y))){
                tiles.remove(idx);
                found = true;
                break;
            }
            else{
                ++idx;
            }
        }
        if (found) fallToFloor();
    }

    public void fallToFloor(){
        boolean flag = false;
        for (Tree tree: trees){
            while(!touchingFloor(tree)) {
                tree.setPosition(tree.moveDown());
                if (tree.getPosition().getY() > height) {
                    trees.remove(tree);
                    flag = true;
                    break;
                }
            }
            if (flag) break;
        }
        for(Player player: players){
            Tank tank = player.getTank();
            while(!tankTouchingFloor(tank)) {
                try {
                    TimeUnit.MILLISECONDS.sleep(125);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tank.setPosition(tank.moveDown());
                if(tank.getPosition().getY() > height){
                    tank.decreaseHealth(1f);
                    player.setDead();
                    endTurn();
                    break;
                }
            }
        }
    }
    public boolean activeShell(){
        return shell != null;
    }


    private boolean tankTouchingFloor(Tank tank){
        return tiles.contains(new Terrain(tank.getPosition().getX(), tank.getPosition().getY() + 1))
                || tiles.contains(new Terrain(tank.getPosition().getX() + 1, tank.getPosition().getY() + 1))
                || tiles.contains(new Terrain(tank.getPosition().getX() + 2, tank.getPosition().getY() + 1))
                || tiles.contains(new Terrain(tank.getPosition().getX() + 3, tank.getPosition().getY() + 1))
                || tiles.contains(new Terrain(tank.getPosition().getX() + 4, tank.getPosition().getY() + 1))
                || tiles.contains(new Terrain(tank.getPosition().getX() + 5, tank.getPosition().getY() + 1))
                || tiles.contains(new Terrain(tank.getPosition().getX() + 6, tank.getPosition().getY() + 1));
    }
    private boolean touchingFloor(Element el) {
        return tiles.contains(new Terrain(el.getPosition().getX(), el.getPosition().getY() + 1));
    }


    public List<Player> getPlayers(){
        return players;
    }
    public Player getActivePlayer() {
        return players.get(player_counter % 2);
    }

    public void endTurn(){
        getActivePlayer().resetSteps();
        player_counter++;
        will_explode = false;
    }


    public void shoot(float speed){
        Player player = getActivePlayer();
        shell = new Shell(player.getTank().getCannonPosition(),speed, getActivePlayer().getAngle());
    }

    public void eraseShell(){
        shell = null;
    }

    public void explode(float x, float y) {
        try {
            TimeUnit.MILLISECONDS.sleep(120);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        destroy(x,y);
        destroy(x+1,y);
        destroy(x-1,y);
        destroy(x,y-1);
        destroy(x,y+1);
        destroy(x-1,y-1);
        destroy(x+1,y+1);
        destroy(x-1,y+1);
        destroy(x+1,y-1);
    }

    public void prepareNextTurn() {
        preparing = true;
    }

    public boolean shouldSwitchTurn() {
        if(!activeShell() && preparing ){
            this.preparing = false;
            this.will_explode = false;
            return true;
        }
        return false;
    }

    public boolean outOfBounds(Shell shell) {
        float x = shell.getPosition().getX();
        float y = shell.getPosition().getY();
        return x < 0 || x > width || y > height;
    }
    public boolean outOfBounds(Player player) {
        float x = player.getTank().getPosition().getX();
        float y = player.getTank().getPosition().getY();
        return x < 0 || x > width || y > height;
    }

    public boolean willExplode(){
        return this.will_explode;
    }

    public void setWillExplode(boolean will_explode) {
        this.will_explode = will_explode;
    }

}

