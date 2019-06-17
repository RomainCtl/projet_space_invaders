package model;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Observable;

public abstract class Entity extends Observable {

    protected static Boolean LEFT = true;
    protected static Boolean RIGHT = false;

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

    public abstract void paint(Graphics g);

    public double getX() {
        return this.posx;
    }
    public double getY() {
        return this.posy;
    }
}