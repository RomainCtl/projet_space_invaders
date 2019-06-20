package controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.Iterator;
import java.util.TimerTask;

import model.Alien;
import model.Bullet;
import model.Game;
import model.Spaceship;
import view.MainInterface;

public class SpaceInvader extends Observable {
    private Timer timer = null;
    private Boolean status;
    private Game game;
    private MainInterface myinterface;

    public static Boolean PAUSE = true;
    public static Boolean IN_GAME = false;

    private int rows, cols;

    private ArrayList<Alien> aliens;
    private ArrayList<Bullet> bullets;
    private Spaceship spaceship;

    public SpaceInvader(int row, int col) {
        this.rows = row;
        this.cols = col;
        this.myinterface = new MainInterface(this);
        this.restart();
    }

    class ScheduleTask extends TimerTask {
        public void run() {
            if (status == SpaceInvader.IN_GAME) {
                checkCollision();
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
        if (this.timer != null)
            this.timer.cancel(); // on annule l'ancien timer s'il existe

        // init interface (game & info)
        this.myinterface.initInterface(this.rows*this.cols);

        // nouvelle partie
        this.game = new Game(this.rows*this.cols);
        this.game.addObserver(this.myinterface);

        // nouveau Spaceship
        this.spaceship = new Spaceship(MainInterface.GAME_W/2-50, MainInterface.GAME_W-100);
        this.aliens = new ArrayList<Alien>();
        this.bullets = new ArrayList<Bullet>();

        // les aliens
        int a=0, b=0;
        for (int i=0 ; i<this.rows*this.cols ; i++) {
            if (a >= this.cols) {
                a=0;
                b++;
            }
            this.aliens.add( new Alien(this, a*Alien.w, b*Alien.h) );
            a++;
        }

        // timer pour les AutoEntity
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new ScheduleTask(), 500, 50);

        // set status in game
        this.status = SpaceInvader.IN_GAME;
    }

    // change game status to pause
    public void setPause() {
        this.status = !this.status;
    }
    // spaceship send bullet
    public void sendBullet() {
        if (status == SpaceInvader.IN_GAME) {
                if(bullets.size() != 0){
                    if(bullets.get(bullets.size()-1).getY() < 450) {
                        this.bullets.add(
                            new Bullet(this.spaceship.getX()+10, this.spaceship.getY())
                        );
                        this.game.addBullet();
                    }
                } else {
                    this.bullets.add(
                        new Bullet(this.spaceship.getX()+10, this.spaceship.getY())
                    );
                    this.game.addBullet();
                }
        }
    }
    // suppression d'un alien, d'un bullet et incrementation du nombre de kill
    public void killAlien(Alien a, Bullet b) {
        if (status == SpaceInvader.IN_GAME) {
            this.aliens.remove(a);
            this.bullets.remove(b);
            this.game.addKill();
        }
    }
    // change ship direction (right or left key)
    public void shipMove(Boolean direction) {
        if (status == SpaceInvader.IN_GAME)
            this.spaceship.move(direction);
    }

    // change alien group direction
    public void alienChangeDirection(Boolean dir) {
        for (Alien a : this.aliens) a.setDirection(dir);
    }

    // use by view to get all aliens
    public ArrayList<Alien> getAliens() {
        return this.aliens;
    }

    // same as aliens but with bullets
    public ArrayList<Bullet> getBullets() {
        return this.bullets;
    }

      // get spaceship object
    public Spaceship getSpaceship(){
        return this.spaceship;
    }

    public void checkCollision(){
        int i=0, y=0;
        for(i=0;i<bullets.size();i++){
            Bullet b = bullets.get(i);
            double b_x = b.getX(); 
            double b_y = b.getY();
            for(y=0;y<aliens.size();y++){
                Alien a = aliens.get(i);
                double a_x = a.getX();
                double a_y = a.getY();
                if(b_y < a_y + a.h) {
                    if(a_x + a.w/3 > b_x && a_x - a.w/3 < b_x) {
                        killAlien(a,b);
                    }
                }
            }
        }
    }
}