package ldts.g0907.diplomacyfailed.game.model;

import java.util.ArrayList;
import java.util.List;

public class Terrain extends Element{
//    String type ??

    private String decal;

    private List<String> colors;
    public Terrain(Position pos) {
        super(pos);
        colors = new ArrayList<>();
        colors.add("#378805"); //Backgorund: green
        colors.add("#26580F"); //Foreground: dark green
        decal = "v";
    }

    public Terrain(float x, float y) {
        super(x, y);
        colors = new ArrayList<>();
        colors.add("#378805"); //Backgorund: green
        colors.add("#26580F"); //Foreground: dark green
        decal = "v";

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;

        Terrain t = (Terrain) obj;
        return position.roughlyEquals(t.position) && decal.equals(t.decal);
    }

    public String getDecal() {
        return decal;
    }

    public void setDecal(String decal) {
        this.decal = decal;
    }
    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }
}
