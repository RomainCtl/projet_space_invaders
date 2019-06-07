package model;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AutoEntity extends Entity {

    private Timer timer;

    public AutoEntity(double x, double y, TimerTask task, int delay, int period) {
        super(x, y);

        timer = new Timer();
        timer.scheduleAtFixedRate(task, delay, period);
    }
}