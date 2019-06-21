package model;

public abstract class AutoEntity extends Entity {

    protected Boolean direction; // true=Left, false=right

    public AutoEntity(double x, double y, int w, int h, int s) {
        super(x, y, w, h, s);
    }

    public abstract Boolean move();
}