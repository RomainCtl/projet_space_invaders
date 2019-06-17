package model;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AutoEntity extends Entity {

    private Timer timer;
    protected Boolean direction; // true=Left, false=right

    public AutoEntity(double x, double y, int w, int h, int delay, int period) {
        super(x, y, w, h);

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