package model;

import controller.SpaceInvader;
import view.MainInterface;

public class Alien extends AutoEntity {

    public static int w = 43;
    public static int h = 29;
    private SpaceInvader instance;

    public Alien(SpaceInvader i, double x, double y, int speed) {
        super(x, y, Alien.w, Alien.h, speed);
        this.direction = Entity.RIGHT;
        this.instance = i;
    }

    @Override
    public Boolean move() {
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
        return this.posy + Alien.h + 100 >= MainInterface.GAME_H;
    }

    public void setDirection(Boolean dir) {
        this.posy += this.height/2;
        this.direction = dir;
    }
}