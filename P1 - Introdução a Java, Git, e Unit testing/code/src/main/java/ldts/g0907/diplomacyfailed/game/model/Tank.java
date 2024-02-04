package ldts.g0907.diplomacyfailed.game.model;

public class Tank extends Entity {
    public final static int MAX_HEALTH = 100;
    public final static int MIN_HEALTH = 1;

    private int health;

    private int angle;

    public int getAngle() {
        return angle;
    }
    public Tank(Position pos) {
        super(pos);
        angle = 0;
        health = MAX_HEALTH;
    }

    public Tank(float x, float y) {
        super(x, y);
        angle = 0;
        health = MAX_HEALTH;
    }
    public Tank(float x, float y, int angle) {
        super(x, y);
        this.angle = angle;
        health = MAX_HEALTH;
    }

    public int getHealth() {
        return health;
    }

    public void decreaseHealth(float dmgPercent){

    }


    public void updateAngle(int change){
        angle += change;
        if (angle > 180) angle = 180;
        else if (angle < 0) angle = 0;
    }

    public void tiltUp(){
        updateAngle(1);
    }
    public void tiltDown(){
        updateAngle(-1);
    }

    public boolean equals(Tank tank1){
        return (this.position.roughlyEquals( tank1.getPosition()) && this.health == tank1.getHealth());
    }
    public void flipRight(){
        if (angle > 90)
        angle = 180 - angle;
    }
    public void flipLeft(){
        if (angle <= 90)
        angle = 180 - angle;
    }

    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public Position getCannonPosition() {
        if (isBetween(angle, 0, 9)) {
            return new Position(position.getX() + 8, position.getY() - 2);
        } else if (isBetween(angle, 10, 20)) {
            return new Position(position.getX() + 9, position.getY() - 1);

        } else if (isBetween(angle, 21, 45)) {
            return new Position(position.getX() + 10, position.getY() - 2);

        } else if (isBetween(angle, 46, 60)) {
            return new Position(position.getX() + 8, position.getY() - 4);

        } else if (isBetween(angle, 61, 80)) {
            return new Position(position.getX() + 7, position.getY() - 4);


        } else if (isBetween(angle, 81, 90)) {
            return new Position(position.getX() + 4, position.getY() - 5);

        } else if (isBetween(angle, 91, 100)) {
            return new Position(position.getX() + 3, position.getY() - 5);

        } else if (isBetween(angle, 101, 120)) {
            return new Position(position.getX() - 1, position.getY() - 4);

        } else if (isBetween(angle, 121, 135)) {
            return new Position(position.getX() - 2, position.getY() - 4);
        } else if (isBetween(angle, 136, 160)) {
            return new Position(position.getX() - 4, position.getY() - 2);

        } else if (isBetween(angle, 161, 170)) {
            return new Position(position.getX() - 3, position.getY() - 2);

        } else if (isBetween(angle, 171, 180)) {
            return new Position(position.getX() - 2, position.getY() - 2);

        } else {
            return new Position(0f, 0f);
        }
    }

    public void setHealth(int new_health) {
        this.health = new_health;
    }
}
