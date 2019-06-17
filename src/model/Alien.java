package model;

public class Alien extends AutoEntity {

    private String image_path = "../assets/alien.gif";

    public static int w = 43;
    public static int h = 29;

    public Alien(double x, double y) {
        super(x, y, Alien.w, Alien.h, 100, 10);
        this.direction = Entity.RIGHT;
    }

    @Override
    public void move() {
        if (this.direction == Entity.LEFT) {
            if (this.posx <= 0) {
                this.posy -= this.height;
                this.direction = !this.direction;
            } else
                this.posx -= 1;
        } else {
            if (this.posx + this.width >= 700) { // FIXME Get panel size
                this.posy -= this.height;
                this.direction = !this.direction;
            } else
                this.posx += 1;
        }
    }
}