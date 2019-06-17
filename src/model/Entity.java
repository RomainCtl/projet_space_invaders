package model;

import java.awt.Image;

public abstract class Entity {

    protected static Boolean LEFT = true;
    protected static Boolean RIGHT = false;

    protected boolean status;

    protected double posx;
	protected double posy;
    protected String src_img;
    protected Image sprite;
    protected int width, height;

    public Entity(double x, double y, int w, int h){
        this.posx = x;
        this.posy = y;
        this.width = w;
        this.height = h;
    }

    public double getX() {
        return this.posx;
    }
    public double getY() {
        return this.posy;
    }
}