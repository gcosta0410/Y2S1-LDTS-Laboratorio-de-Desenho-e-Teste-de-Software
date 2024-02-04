package ldts.g0907.diplomacyfailed.game.model;

public class Entity extends Element{
    public Entity(Position pos) {
        super(pos);
    }
    public Entity(float x, float y) {
        super(x, y);
    }

    public Position move(float x, float y) { return new Position(position.getX() + x, position.getY() + y); }
    public Position moveUp() { return move(0.0F, -1.0F); }
    public Position moveDown() { return move(0.0F, 1.0F); }
    public Position moveLeft() { return move(-1.0F, 0.0F); }
    public Position moveRight() { return move(1.0F, 0.0F); }

}
