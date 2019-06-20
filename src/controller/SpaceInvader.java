package controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import model.Alien;
import model.Bullet;
import model.GameConfig;
import model.Game;
import model.Spaceship;
import view.MainInterface;

public class SpaceInvader extends Observable {
    private Timer timer = null;
    private Boolean status;
    private Game game;
    private GameConfig config;
    private MainInterface myinterface;

    public static Boolean PAUSE = true;
    public static Boolean IN_GAME = false;

    private Boolean over;
    private int rows, cols, speed;

    private int interval = 50; // 0.05s
    private int cpt_new_bullet;
    private Boolean new_bullet_flag = true;

    private ArrayList<Alien> aliens;
    private ArrayList<Bullet> bullets;
    private Spaceship spaceship;

    public SpaceInvader(int row, int col) {
        this.rows = row;
        this.cols = col;
        this.config = new GameConfig();
        this.myinterface = new MainInterface(this, this.config);
        this.restart();
    }

    class ScheduleTask extends TimerTask {
        public void run() {
            if (status == SpaceInvader.IN_GAME) {
                // create new army each X time
                if (aliens.size() ==0){
                    speed++;
                    createArmy();
                }

                // can send bullet only each
                cpt_new_bullet++;
                if (cpt_new_bullet >= config.getBullet_interval().getInterval()) {
                    new_bullet_flag = true;
                }

                ArrayList<Bullet> to_delete = new ArrayList<Bullet>();
                checkCollision();
                for (Alien a : aliens)
                    if (a.move()) {
                        end();
                        return;
                    }
                for (Bullet b : bullets)
                    if (b.move())
                        to_delete.add(b);

                // remove bullet thats was not on screen
                for (Bullet b : to_delete)
                    bullets.remove(b);
                setChanged();
                notifyObservers();
            }
        }
    }

    // create new army
    public void createArmy(){
        int a=0, b=0;
        for (int i=0 ; i<this.rows*this.cols ; i++) {
            if (a >= this.cols) {
                a=0;
                b++;
            }
            this.aliens.add(
                new Alien(this, a*Alien.w, b*Alien.h, this.speed)
            );
            a++;
        }
        this.game.addArmy();
    }

    // spaceship send bullet
    public void sendBullet() {
        if (new_bullet_flag) {
            this.bullets.add(
                new Bullet(this, this.spaceship.getX()+10, this.spaceship.getY())
            );
            this.game.addBullet();
            new_bullet_flag = false;
            cpt_new_bullet = 0;
        }
    }

    // restart game
    public void restart() {
        this.speed = this.config.getAlien_speed().getSpeed();
        this.over=false;

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
        this.aliens = new ArrayList<Alien>();

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
    public void setPause(Boolean p) {
        this.status = p;
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
        for (Alien a : aliens) a.setDirection(dir);
    }

    // get aliens list
    public ArrayList<Alien> getAliens() {
        return this.aliens;
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

    public Boolean getOver(){
        return this.over;
    }

    public void end(){
        this.status = SpaceInvader.PAUSE;
        this.over=true;
        setChanged();
        notifyObservers();
    }

    public void checkCollision(){
        for (int i = this.bullets.size()-1 ; i>=0 ; i--) {
            Bullet b = this.bullets.get(i);
            double b_x = b.getX();
            double b_y = b.getY();

            for (int y = this.aliens.size()-1 ; y>=0 ; y--) {
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