package model;

import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable {

    private ArrayList<Alien> aliens;
    private ArrayList<Bullet> bullets;
    private Spaceship spaceship;

    private int nb_bullet_send;
    private int nb_kill;

    public Game(int nb_alien){
        spaceship = new Spaceship(700 /2, 700-50); // FIXME size of panel
        aliens = new ArrayList<Alien>();
        bullets = new ArrayList<Bullet>();

        int a=0, b=0;
        for (int i=0 ; i<nb_alien ; i++) {
            if (a*Alien.w >= 700) { // new line FIXME size of panel
                a=0;
                b++;
            }
            aliens.add( new Alien(a*Alien.w, b*Alien.h) );
        }

        this.nb_bullet_send = 0;
        this.nb_kill = 0;
    }

    public void sendBullet() {
        this.bullets.add(
            new Bullet(this.spaceship.getX(), this.spaceship.getY())
        );
        this.nb_bullet_send +=1;
    }

    public void killAlien(int i) {
        this.aliens.remove(i);
        this.nb_kill += 1;
    }

    public int getNbKill() {
        return this.nb_kill;
    }
    public int getNbBulletSend() {
        return this.nb_bullet_send;
    }
    public double getRatio() {
        return this.nb_kill / (this.nb_bullet_send *1.0);
    }
}