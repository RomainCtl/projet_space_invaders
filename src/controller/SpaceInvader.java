package controller;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import model.Entity;
import model.Game;
import view.MainInterface;

public class SpaceInvader {
    private Timer timer;
    private Boolean status;
    private Game game;
    private MainInterface myinterface;

    public static Boolean PAUSE = true;
    public static Boolean IN_GAME = false;

    public SpaceInvader() {
        this.myinterface = new MainInterface(this);
        this.restart();
    }

    class ScheduleTask extends TimerTask {
        public void run() {
            if (status == SpaceInvader.IN_GAME) {
                // move();
            }
        }
    }

    public void setPause() {
        this.status = !this.status;
    }

    public void restart() {
        this.game = new Game(5*11);
        this.game.addObserver(this.myinterface);

        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new ScheduleTask(), 100, 10);

        this.status = SpaceInvader.IN_GAME;
    }

    public void sendBullet() {
        this.game.addBullet();
    }

    public void killAlien(int i) {
        this.game.removeAlien(i);
    }
}