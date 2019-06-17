package model;

public abstract class AutoEntity extends Entity {

    protected Boolean direction; // true=Left, false=right

    public AutoEntity(double x, double y, int w, int h, int delay, int period) {
        super(x, y, w, h);
    }

    public abstract void move();
}