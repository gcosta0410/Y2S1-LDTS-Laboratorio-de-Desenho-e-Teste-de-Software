package ldts.g0907.diplomacyfailed.game.model;

public class Player {

    private Tank tank;
    private String name;
    private boolean dead;

    private int steps;
    public final static int MAX_STEPS = 5;
    String color;
    String ORIGINAL_COLOR;

    public Player(Tank tank, String name) {
        this.name = name;
        this.tank = tank;
        this.dead = false;
        this.steps = 0;
    }
    public Player(Tank tank, String name, String color) {
        this.name = name;
        this.tank = tank;
        this.dead = false;
        this.color = color;
        this.ORIGINAL_COLOR = color;
        this.steps = 0;
    }

    public Tank getTank() {
        return this.tank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDead() {
        this.dead = true;
        this.color = "000000";
    }

    public boolean isDead() {
        return dead;
    }

    public String getColor() {
        return color;
    }


    public int getAngle(){
        return tank.getAngle();
    }
    public void updateAngle(int increment) {tank.updateAngle(increment);}
    public void tiltUp(){
        tank.tiltUp();
    }
    public void tiltDown(){
        tank.tiltDown();
    }
    public void flipLeft(){tank.flipLeft();}
    public void flipRight(){tank.flipRight();}


    public String getOriginalColor() {
        return ORIGINAL_COLOR;
    }
    public void setColor(String color){
        this.color = color;
    }

    public int getSteps() {
        return steps;
    }

    public void resetSteps(){
        steps = 0;
    }
    public void increaseSteps(){
        steps++;
    }
}
