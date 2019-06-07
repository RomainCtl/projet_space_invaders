package model;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AutoEntity extends Entity {

    private Timer timer;

    public AutoEntity(double x, double y, int delay, int period) {
        super(x, y);

        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), delay, period);
    }

    class ScheduleTask extends TimerTask {
        public void run() {
            move();
            setChanged();
            notifyObservers();
        }
    }

    public abstract void move();
}