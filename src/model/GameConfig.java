package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Observable;
import java.util.Properties;

import model.enums.BulletSpeed;
import model.enums.Level;

public class GameConfig extends Observable {

    private String path = "config.properties";

    private String alien_image;
    private String bullet_image;
    private String spaceship_image;
    private String background_image;
    private String gameover_image;
    private BulletSpeed bullet_interval;
    private Level alien_speed;
    private int number_alien_row;
    private int number_alien_col;

    public GameConfig() {
        this.read();
    }

    private void read() {
        try (InputStream input = new FileInputStream(this.path)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value
            this.alien_image = prop.getProperty("alien_image");
            this.bullet_image = prop.getProperty("bullet_image");
            this.spaceship_image = prop.getProperty("spaceship_image");
            this.background_image = prop.getProperty("background_image");
            this.gameover_image = prop.getProperty("gameover_image");
            this.number_alien_row = Integer.valueOf(prop.getProperty("number_alien_row"));
            this.number_alien_col = Integer.valueOf(prop.getProperty("number_alien_col"));

            int as = Integer.valueOf(prop.getProperty("alien_speed"));
            int bi = Integer.valueOf(prop.getProperty("bullet_interval"));
            this.bullet_interval = BulletSpeed.values()[bi];
            this.alien_speed = Level.values()[as];

        } catch (IOException ex) {
            //ex.printStackTrace();
            // set default value and write
            this.alien_image = "../assets/alien.gif";
            this.bullet_image = "../assets/shot.gif";
            this.spaceship_image = "../assets/ship.gif";
            this.background_image = "../assets/background.png";
            this.gameover_image = "../assets/gameover.gif";
            this.number_alien_row = 5;
            this.number_alien_col = 11;
            this.bullet_interval = BulletSpeed.NORMALE;
            this.alien_speed = Level.NORMALE;
        }
    }
    private void write() {
        try (OutputStream output = new FileOutputStream(this.path)) {

            Properties prop = new Properties();

            // set the properties value
            prop.setProperty("alien_image", this.alien_image);
            prop.setProperty("bullet_image", this.bullet_image);
            prop.setProperty("spaceship_image", this.spaceship_image);
            prop.setProperty("background_image", this.background_image);
            prop.setProperty("gameover_image", this.gameover_image);
            prop.setProperty(
                "bullet_interval",
                String.valueOf(this.bullet_interval.ordinal())
            );
            prop.setProperty(
                "alien_speed",
                String.valueOf(this.alien_speed.ordinal())
            );
            prop.setProperty("number_alien_row", String.valueOf(this.number_alien_row));
            prop.setProperty("number_alien_col", String.valueOf(this.number_alien_col));

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    // observable
    private void notif(){
        this.write();
        this.setChanged();
        this.notifyObservers();
    }

    // setter & getter

    public void setNumber_alien_col(int number_alien_col){
        this.number_alien_col = number_alien_col;
        this.notif();
    }
    public int getNumber_alien_col(){
        return this.number_alien_col;
    }
    public void setNumber_alien_row(int number_alien_row){
        this.number_alien_row = number_alien_row;
        this.notif();
    }
    public int getNumber_alien_row(){
        return this.number_alien_row;
    }
    // public void setAlien_speed(int alien_speed){
    //     this.alien_speed = Level.values()[alien_speed];
    //     this.notif();
    // }
    public void setNextAlienSpeed() {
        this.alien_speed = this.alien_speed.next();
        this.notif();
    }
    public void setAlien_speed(Level a) {
        this.alien_speed = a;
        this.notif();
    }
    public Level getAlien_speed(){
        return this.alien_speed;
    }
    public void setNextBulletInterval() {
        this.bullet_interval = this.bullet_interval.next();
        this.notif();
    }
    public void setBullet_interval(BulletSpeed b) {
        this.bullet_interval = b;
        this.notif();
    }
    public BulletSpeed getBullet_interval(){
        return this.bullet_interval;
    }
    public void setBackground_image(String background_image){
        this.background_image = background_image;
        this.notif();
    }
    public String getBackground_image(){
        return this.background_image;
    }
    public void setSpaceship_image(String spaceship_image){
        this.spaceship_image = spaceship_image;
        this.notif();
    }
    public String getSpaceship_image(){
        return this.spaceship_image;
    }
    public void setBullet_image(String bullet_image){
        this.bullet_image = bullet_image;
        this.notif();
    }
    public String getBullet_image(){
        return this.bullet_image;
    }
    public void setAlien_image(String alien_image){
        this.alien_image = alien_image;
        this.notif();
    }
    public String getAlien_image(){
        return this.alien_image;
    }
    public String getGameover_image(){
        return this.gameover_image;
    }
}