package model;

public class Alien extends AutoEntity {

    private Boolean direction; // true=Left, false=right
    private int width, height;

    public Alien(double x, double y){
        super(x, y, 100, 10);
    }

    public void move() {
        if (this.direction == Entity.LEFT) {
            if (this.posx + this.width >= 700) // FIXME Get panel size
                this.posy -= this.height;
            else
                this.posx += 1;
        } else {
            if (this.posx <= 0)
                this.posy -= this.height;
            else
                this.posx -= 1;
        }
    }
}