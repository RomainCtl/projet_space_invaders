package model;

import controller.SpaceInvader;

public class Bullet extends AutoEntity {

    public static int w = 12;
    public static int h = 23;

    private SpaceInvader instance;

    public Bullet(SpaceInvader instance, double x, double y) {
        super(x, y, Bullet.w, Bullet.h, 100, 10);
        this.instance=instance;
    }

    @Override
    public Boolean move() {
        if (this.posy + this.height >= 0)
            this.posy -= 10;
        return this.posy + this.h < 0;
    }
}