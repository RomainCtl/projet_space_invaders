package model;

public abstract class Entity {

    public static Boolean LEFT = true;
    public static Boolean RIGHT = false;

    protected boolean status;

    protected double posx;
	protected double posy;
    protected int width, height;
    protected int speed;

    public Entity(double x, double y, int w, int h, int s){
        this.posx = x;
        this.posy = y;
        this.width = w;
        this.height = h;
        this.speed = s
    }

    public double getX() {
        return this.posx;
    }
    public double getY() {
        return this.posy;
    }
}