package ldts.g0907.diplomacyfailed.game.model;

public class Element { //Entities and terrain tiles?
    protected Position position;

    public Element(Position pos) {
        this.position = pos;
    }

    public Element(float x, float y) {
        position = new Position (x,y);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position pos) {
        this.position = pos;
    }
}
