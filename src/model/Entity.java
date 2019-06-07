package model;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Observable;

public abstract class Entity extends Observable {

    private double posx, posy;
    private String src_img;
    private Image sprite;

    public Entity(double x, double y){
        this.posx = x;
        this.poxy = y;
    }

    public void goLeft() { this.x -= 1; }
    public void goRight() { this.x += 1; }
    public void goBottom() { this.y -= 1; }
    public void goTop() { this.y += 1; }

    public abstract void paint(Graphics g);
}