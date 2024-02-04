package ldts.g0907.diplomacyfailed.game.model;

import static java.lang.Math.abs;

public class Position {
    private static final float TOLERABLE_ERROR = 0.001F;
    private float x;
    private float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        Position p = (Position) obj;
        return (abs(x - p.getX()) < Math.ulp(x) && abs(y - p.getY()) < Math.ulp(y));
    }

    public boolean roughlyEquals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        Position p = (Position) obj;
        return (abs(x - p.getX()) < TOLERABLE_ERROR && abs(y - p.getY()) < TOLERABLE_ERROR);
    }

    public boolean roughlyEquals(Float x2, Float y2) {
        return (abs(x - x2) < TOLERABLE_ERROR && abs(y - y2) < TOLERABLE_ERROR);

    }

    public boolean sameTile(Object obj){
        if (this == obj) return true;
        if (obj == null) return false;
        Position p = (Position) obj;
        return Math.round(x) == Math.round(p.x) && Math.round(y) == Math.round(p.y) ;
    }
    public boolean sameTile(float x2, float y2){
        return Math.round(x) == Math.round(x2) && Math.round(y) == Math.round(y2) ;
    }
}
