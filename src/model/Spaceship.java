package model;

import controller.SpaceInvader;
import view.MainInterface;

public class Spaceship extends Entity {

    private String image_path = "../assets/ship.gif";

    public static int w = 33;
    public static int h = 23;

    public Spaceship(double x, double y) {
        super(x, y, Spaceship.w, Spaceship.h);
    }

    public void move(Boolean direction) {
        if (status == SpaceInvader.IN_GAME) {
            if (direction == Entity.LEFT) {
                if (this.posx > 0)
                    this.posx -= 2;
            } else {
                if (this.posx + this.width < MainInterface.GAME_W)
                    this.posx += 2;
            }
        }
    }
}