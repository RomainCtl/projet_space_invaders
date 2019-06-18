package model;

import controller.SpaceInvader;
import view.MainInterface;

public class Alien extends AutoEntity {

    public static int w = 43;
    public static int h = 29;
    public int speed = 1;
    private SpaceInvader instance;

    public Alien(SpaceInvader i, double x, double y) {
        super(x, y, Alien.w, Alien.h, 100, 10);
        this.direction = Entity.RIGHT;
        this.instance = i;
    }

    @Override
    public void move() {
        if (this.direction == Entity.LEFT) {
            if (this.posx <= 0)
                this.instance.alienChangeDirection(!this.direction);
            else
                this.posx -= this.speed;
        } else {
            if (this.posx + this.width >= MainInterface.GAME_W)
                this.instance.alienChangeDirection(!this.direction);
            else
                this.posx += this.speed;
        }
    }

    public void setDirection(Boolean dir) {
        this.posy += this.height/3*2;
        this.direction = dir;
    }
}