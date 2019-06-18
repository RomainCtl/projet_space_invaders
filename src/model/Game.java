package model;

import java.util.Observable;

public class Game extends Observable {

    private int nb_bullet_send;
    private int nb_kill;

    public Game(){
        this.nb_bullet_send = 0;
        this.nb_kill = 0;
    }

    public void addBullet() {
        this.nb_bullet_send +=1;
    }
    public void addKill() {
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