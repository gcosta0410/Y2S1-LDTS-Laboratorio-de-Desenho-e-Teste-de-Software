package ldts.g0907.diplomacyfailed.game.model;

import static java.lang.Math.abs;

public class Shell extends Entity{

private final static float GRAVITY = -20F;

    private final float initial_speed;
    private final float initial_x;
    private final float initial_y;
    private final float initial_angle;
    private final float x_speed;
    private float y_speed;
    float delta = 0.005F;
    private boolean active;

    public Shell(Position pos, float initial_speed, int angle) {
        super(pos);
        this.initial_speed = initial_speed;
        this.initial_angle = (float) (2*Math.PI * (angle / (float)360));
        this.x_speed = initial_speed * (float)Math.cos(initial_angle);
        this.y_speed = initial_speed * (float)Math.sin(initial_angle);
        this.initial_x = position.getX();
        this.initial_y = position.getY();
        this.active = true;
    }

    public Shell(float x, float y, float initial_speed, int angle) {
        super(x, y);
        this.initial_speed = initial_speed;
        this.initial_angle = (float) (2*Math.PI * (angle / (float)360));
        this.x_speed = initial_speed * (float)Math.cos(initial_angle);
        this.y_speed = initial_speed * (float)Math.sin(initial_angle);
        this.initial_x = position.getX();
        this.initial_y = position.getY();

        this.active = true;
    }


    public void endShot(){this.active = false;}

    public boolean roughlyEquals(float a, float b){
        return abs(a - b) < 0.0001;
    }

    public Position nextPosition(){
        float next_x = position.getX() + x_speed * delta;
        float next_y = position.getY();
        if(roughlyEquals(initial_angle,(float)Math.PI / 2) || initial_speed == 0){
            next_y -= y_speed * delta;
            y_speed +=  GRAVITY * delta;
        }
        else{
            next_y = initial_y - (float)((next_x - initial_x) * Math.tan(initial_angle) + (GRAVITY * Math.pow((next_x - initial_x),2) ) / (2 * Math.pow(x_speed,2)));
        }
        return new Position(next_x, next_y);
    }

    public Position nextIntegerPosition(){
        float x = position.getX();
        float y = position.getY();
        while(Math.round(x) == initial_x && Math.round(y) == initial_y ) {
            if(roughlyEquals(initial_angle,(float)Math.PI / 2)){
                y -= y_speed * delta;
                y_speed +=  GRAVITY * delta;
            }
            else {
                x += x_speed * delta;
                y = initial_y - (float) ((x - initial_x) * Math.tan(initial_angle) + (GRAVITY * Math.pow((x - initial_x), 2)) / (2 * Math.pow(x_speed, 2)));
            }
        }

        return new Position(Math.round(x), Math.round(y));
    }

    public float getX_speed() {
        return x_speed;
    }

    public float getY_speed() {
        return y_speed;
    }

    public boolean isActive() {
        return active;
    }
}
