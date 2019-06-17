package model;

public class Bullet extends AutoEntity {

    private String image_path = "../assets/shot.gif";

    public static int w = 12;
    public static int h = 23;

    public Bullet(double x, double y) {
        super(x, y, Bullet.w, Bullet.h, 100, 10);
    }

    @Override
    public void move() {
        if (this.posy + this.height >= 0)
            this.posy -= 3;
    }
}