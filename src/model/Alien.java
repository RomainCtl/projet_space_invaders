package model;

import controller.SpaceInvader;
import view.MainInterface;

public class Alien extends AutoEntity {

    public static int w = 43;
    public static int h = 29;
    public int speed;
    private SpaceInvader instance;
    private int army_id;

    public Alien(SpaceInvader i, double x, double y, int speed, int army_id) {
        super(x, y, Alien.w, Alien.h, 100, 10);
        this.direction = Entity.RIGHT;
        this.instance = i;
        this.speed = speed;
        this.army_id = army_id;
    }

    @Override
    public void move() {
        if (this.direction == Entity.LEFT) {
            if (this.posx <= 0)
                this.instance.alienChangeDirection(this.army_id, !this.direction);
            else
                this.posx -= this.speed;
        } else {
            if (this.posx + this.width >= MainInterface.GAME_W)
                this.instance.alienChangeDirection(this.army_id, !this.direction);
            else
                this.posx += this.speed;
        }
    }

    public void setDirection(Boolean dir) {
        this.posy += this.height/2;
        this.direction = dir;
        if (this.posy + Alien.h >= MainInterface.GAME_H) this.instance.end();
    }

    public int getArmy() {
        return this.army_id;
    }
}