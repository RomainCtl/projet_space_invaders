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
    private Timer timer = null;
    private Boolean status;
    private Game game;
    private MainInterface myinterface;

    public static Boolean PAUSE = true;
    public static Boolean IN_GAME = false;

    private int rows, cols, speed;

    private int interval = 50; // 0.05s
    private int new_army_interval = 1200; // 1200 * 0.05s = 60s=1min
    private int cpt_new_army;

    private ArrayList<ArrayList<Alien>> army;
    private ArrayList<Bullet> bullets;
    private Spaceship spaceship;

    public SpaceInvader(int row, int col) {
        this.rows = row;
        this.cols = col;
        this.speed = 1;
        this.myinterface = new MainInterface(this);
        this.restart();
    }

    class ScheduleTask extends TimerTask {
        public void run() {
            if (status == SpaceInvader.IN_GAME) {
                // create new army each X time
                cpt_new_army++;
                if (cpt_new_army >= new_army_interval) {
                    if (new_army_interval > 400) new_army_interval-=100;
                    cpt_new_army=0;
                    speed++;
                    createArmy();
                }

                checkCollision();
                for (ArrayList<Alien> aliens : army)
                    for (Alien a : aliens)
                        a.move();
                for (Bullet b : bullets)
                    b.move();
                setChanged();
                notifyObservers();
            }
        }
    }

    // create new army
    public void createArmy(){
        ArrayList<Alien> a_tmp = new ArrayList<Alien>();
        int a=0, b=0;
        for (int i=0 ; i<this.rows*this.cols ; i++) {
            if (a >= this.cols) {
                a=0;
                b++;
            }
            a_tmp.add(
                new Alien(this, a*Alien.w, b*Alien.h, this.speed, this.army.size())
            );
            a++;
        }
        this.army.add(a_tmp);
    }

    // restart game
    public void restart() {
        this.new_army_interval = 1200;
        this.cpt_new_army = 0;

        if (this.timer != null)
            this.timer.cancel(); // on annule l'ancien timer s'il existe

        // init interface (game & info)
        this.myinterface.initInterface(this.rows*this.cols);

        // nouvelle partie
        this.game = new Game(this.rows*this.cols);
        this.game.addObserver(this.myinterface);

        // nouveau Spaceship
        this.spaceship = new Spaceship(MainInterface.GAME_W/2-50, MainInterface.GAME_W-100);
        this.bullets = new ArrayList<Bullet>();
        this.army = new ArrayList<ArrayList<Alien>>();

        // les aliens
        this.createArmy();

        // timer pour les AutoEntity
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new ScheduleTask(), 1000, interval);

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
                            new Bullet(this, this.spaceship.getX()+10, this.spaceship.getY())
                        );
                        this.game.addBullet();
                    }
                } else {
                    this.bullets.add(
                        new Bullet(this, this.spaceship.getX()+10, this.spaceship.getY())
                    );
                    this.game.addBullet();
                }
        }
    }
    // suppression d'un alien, d'un bullet et incrementation du nombre de kill
    public void killAlien(Alien a, Bullet b) {
        if (status == SpaceInvader.IN_GAME) {
            // this.aliens.remove(a);
            this.army.get(a.getArmy()).remove(a);
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
    public void alienChangeDirection(int army_id, Boolean dir) {
        ArrayList<Alien> a_tmp = this.army.get(army_id);
        for (Alien a : a_tmp) a.setDirection(dir);
    }

    // use by view to get all aliens
    public ArrayList<ArrayList<Alien>> getArmy() {
        return this.army;
    }

    // same as aliens but with bullets
    public ArrayList<Bullet> getBullets() {
        return this.bullets;
    }

    public void removeBullet(Bullet b) {
        this.bullets.remove(b);
    }

    // get spaceship object
    public Spaceship getSpaceship(){
        return this.spaceship;
    }
<<<<<<< HEAD
=======

    public void end(){
        this.status = SpaceInvader.PAUSE;
        // TODO
    }

    public void checkCollision(){
        for (int i = this.bullets.size()-1 ; i>=0 ; i--) {
            Bullet b = this.bullets.get(i);
            double b_x = b.getX();
            double b_y = b.getY();

            for (ArrayList<Alien> aliens : army) {
                for (int y = aliens.size()-1 ; y>=0 ; y--) {
                    Alien a = aliens.get(y);
                    double a_x = a.getX();
                    double a_y = a.getY();

                    //check
                    if (b_y <= a_y+a.h && b_y >= a_y && b_x >= a_x && b_x <= a_x+a.w) {
                        killAlien(a, b);
                        return;
                    }
                }
            }
        }
    }
>>>>>>> eeef90734c6007ceb64b962141db12c17cf9e346
}