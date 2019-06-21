package model;

public class Bullet extends AutoEntity {

    public static int w = 12;
    public static int h = 23;

    public Bullet(double x, double y) {
        super(x, y, Bullet.w, Bullet.h, 10);
    }

    @Override
    public Boolean move() {
        if (this.posy + this.height >= 0)
            this.posy -= this.speed;
        return this.posy + Bullet.h < 0;
    }
}