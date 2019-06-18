package controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import model.Alien;
import model.Bullet;
import model.Game;
import model.Spaceship;
import view.MainInterface;

public class SpaceInvader extends Observable {
    private Timer timer;
    private Boolean status;
    private Game game;
    private MainInterface myinterface;

    public static Boolean PAUSE = true;
    public static Boolean IN_GAME = false;

    private ArrayList<Alien> aliens;
    private ArrayList<Bullet> bullets;
    private Spaceship spaceship;

    public SpaceInvader() {
        this.myinterface = new MainInterface(this);
        this.restart();
    }

    class ScheduleTask extends TimerTask {
        public void run() {
            if (status == SpaceInvader.IN_GAME) {
                for (Alien a : aliens)
                    a.move();
                for (Bullet b : bullets)
                    b.move();
                setChanged();
                notifyObservers();
            }
        }
    }

    public void restart() {
        this.game = new Game();
        this.game.addObserver(this.myinterface);

        this.spaceship = new Spaceship(MainInterface.GAME_W /2, MainInterface.GAME_W-50);
        this.aliens = new ArrayList<Alien>();
        this.bullets = new ArrayList<Bullet>();

        int a=0, b=0;
        for (int i=0 ; i<5*11 ; i++) {
            if (a*Alien.w >= MainInterface.GAME_W) {
                a=0;
                b++;
            }
            this.aliens.add( new Alien(a*Alien.w, b*Alien.h) );
        }

        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new ScheduleTask(), 100, 10);

        this.status = SpaceInvader.IN_GAME;
    }

    public void setPause() {
        this.status = !this.status;
    }
    public void sendBullet() {
        if (status == SpaceInvader.IN_GAME) {
            this.bullets.add(
                new Bullet(this.spaceship.getX(), this.spaceship.getY())
            );
            this.game.addBullet();
        }
    }
    public void killAlien(int i) {
        if (status == SpaceInvader.IN_GAME) {
            this.aliens.remove(i);
            this.game.addKill();
        }
    }
    public void shipMove(Boolean direction) {
        if (status == SpaceInvader.IN_GAME)
            this.spaceship.move(direction);
    }

    public ArrayList<Alien> getAliens() {
        return this.aliens;
    }
    public ArrayList<Bullet> getBullets() {
        return this.bullets;
    }
}