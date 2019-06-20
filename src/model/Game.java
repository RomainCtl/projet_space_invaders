package model;

import java.util.Observable;

public class Game extends Observable {

    private int nb_bullet_send;
    private int nb_enemies, nb_remaining_enemies;
    private int nb_alien_by_army;

    public Game(int nb_e_by_army) {
        this.nb_bullet_send = 0;
        this.nb_enemies = this.nb_remaining_enemies = 0;
        this.nb_alien_by_army = nb_e_by_army;
    }

    public void addBullet() {
        this.nb_bullet_send +=1;
        this.notif();
    }
    public void addKill() {
        this.nb_remaining_enemies -= 1;
        this.notif();
    }

    private void notif() {
        this.setChanged();
        this.notifyObservers();
    }

    public void addArmy() {
        this.nb_enemies += this.nb_alien_by_army;
        this.nb_remaining_enemies += this.nb_alien_by_army;
        this.notif();
    }

    public int getNbAlienByArmy() {
        return this.nb_alien_by_army;
    }
    public int getNbRemainingEnemies() {
        return this.nb_remaining_enemies;
    }
    public int getNbKill() {
        return this.nb_enemies - this.nb_remaining_enemies;
    }
    public int getNbBulletSend() {
        return this.nb_bullet_send;
    }
    public double getRatio() {
        return this.getNbKill() / (this.nb_bullet_send *1.0);
    }
}