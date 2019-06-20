package model.enums;

import java.util.Arrays;

public enum BulletSpeed {
    NORMALE("Normale", 16),
    RAPIDE("Rapide", 8),
    OVERKILL("Overkill", 4);

    private String name;
    private int interval;

    BulletSpeed(String name, int interval){
        this.name = name;
        this.interval = interval;
    }

    public BulletSpeed next() {
        return values()[(this.ordinal()+1) % values().length];
    }

    public String getName(){
        return this.name;
    }
    public int getInterval(){
        return this.interval;
    }

    public static String[] names() {
        return Arrays.toString(BulletSpeed.values()).replaceAll("^.|.$", "").split(", ");
    }
}