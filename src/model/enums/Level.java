package model.enums;

import java.util.Arrays;

public enum Level {
    NORMALE("Normale", 1),
    DIFFICILE("Difficile", 3),
    HARDCORE("Hardcore", 6);

    private String name;
    private int speed;

    Level(String name, int speed){
        this.name = name;
        this.speed = speed;
    }

    public Level next() {
        return values()[(this.ordinal()+1) % values().length];
    }

    public String getName() {
        return this.name;
    }
    public int getSpeed() {
        return this.speed;
    }

    public static String[] names() {
        return Arrays.toString(Level.values()).replaceAll("^.|.$", "").split(", ");
    }
}