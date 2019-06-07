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

    public Entity(double x, double y){
        this.posx = x;
        this.poxy = y;
    }

    public abstract void paint(Graphics g);
}