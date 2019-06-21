package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.SpaceInvader;
import model.Alien;
import model.Spaceship;
import model.Bullet;
import model.GameConfig;

public class GameArea extends JPanel implements Observer {
    private static final long serialVersionUID = 2959271263636245674L;
    private String alien_image_path;
    private String bullet_image_path;
    private String ship_image_path;
    private String background_image_path;
    private String gameover_path;

    private Image img_alien;
    private Image img_ship;
    private Image img_bullet;
    private Image img_background;
    private Image img_gameover;

    private Boolean gameover_flag = false;

    private Vector<Vector<Double>> coordAlien;
    private Vector<Double> coordShip;
    private Vector<Vector<Double>> coordBullet;

    public GameArea(GameConfig conf) {
        this.alien_image_path = conf.getAlien_image();
        this.bullet_image_path = conf.getBullet_image();
        this.ship_image_path = conf.getSpaceship_image();
        this.background_image_path = conf.getBackground_image();
        this.gameover_path = conf.getGameover_image();

        this.setBackground(Color.BLACK);
        this.setSize(MainInterface.GAME_W, MainInterface.GAME_H);

        coordAlien = new Vector<Vector<Double>>();
        coordBullet = new Vector<Vector<Double>>();
        coordShip = new Vector<Double>();

        this.setImages();
    }

    private void setImages() {
        if (this.ship_image_path == null || this.alien_image_path == null || this.bullet_image_path == null) {
            System.err.println("Les images des entitées ne peuvent pas être null !");
            throw new ExceptionInInitializerError();
        }

        URL img_space_url = this.getClass().getResource(this.ship_image_path);
        URL img_alien_url = this.getClass().getResource(this.alien_image_path);
        URL img_bullet_url = this.getClass().getResource(this.bullet_image_path);
        URL img_bbg_url = this.getClass().getResource(this.background_image_path);
        URL img_gameover_url = this.getClass().getResource(this.gameover_path);

        if (img_alien_url == null || img_space_url == null || img_bullet_url == null) {
            System.err.println("Impossible de trouver les images");
            throw new ExceptionInInitializerError();
        }
        else {
            ImageIcon ii1 = new ImageIcon(img_alien_url);
            ImageIcon ii2 = new ImageIcon(img_space_url);
            ImageIcon ii3 = new ImageIcon(img_bullet_url);
            ImageIcon ii4 = new ImageIcon(img_bbg_url);
            ImageIcon ii5 = new ImageIcon(img_gameover_url);
            this.img_alien = ii1.getImage();
            this.img_ship = ii2.getImage();
            this.img_bullet = ii3.getImage();
            this.img_background = ii4.getImage();
            this.img_gameover = ii5.getImage();
        }
    }

    // refresh interface (aliens, bullets and spaceship)
    public void paint(Graphics g) {
        super.paint(g);

        // Wallpaper
        g.drawImage(this.img_background, 0, 0, MainInterface.GAME_W, MainInterface.GAME_H, null);

        // Alien
        Graphics2D g2d = (Graphics2D) g;
        for (Vector<Double> a : this.coordAlien) {
            g2d.drawImage(
                img_alien, a.get(0).intValue(), a.get(1).intValue(), this
            );
        }

        //Bullet
        for (Vector<Double> c : this.coordBullet) {
            g2d.drawImage(
                img_bullet, c.get(0).intValue(), c.get(1).intValue(), this
            );
        }

        //Ship
        if (this.coordShip.size() == 2) {
            g2d.drawImage(
                img_ship, coordShip.get(0).intValue(), coordShip.get(1).intValue(), this
            );
        }

        if (this.gameover_flag) {
            g2d.drawImage(
                img_gameover, 0, 0, MainInterface.GAME_W, MainInterface.GAME_H, this
            );
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    // entity positions change from Model
    @Override
    public void update(Observable m, Object o) {
        if (m instanceof SpaceInvader) {
            coordAlien = new Vector<Vector<Double>>();
            coordBullet = new Vector<Vector<Double>>();
            coordShip = new Vector<Double>();

            this.gameover_flag = ((SpaceInvader) m).getOver();

            ArrayList<Alien> aliens = ((SpaceInvader) m).getAliens();
            for (Alien a : aliens) {
                Vector<Double> tmp1 = new Vector<Double>();
                tmp1.add(a.getX()); // x
                tmp1.add(a.getY()); // y
                coordAlien.add(tmp1);
            }

            ArrayList<Bullet> bullet = ((SpaceInvader) m).getBullets();
            for (Bullet b : bullet) {
                Vector<Double> tmp2 = new Vector<Double>();
                tmp2.add(b.getX()); // x
                tmp2.add(b.getY()); // y
                coordBullet.add(tmp2);
            }

            Spaceship spaceship = ((SpaceInvader) m).getSpaceship();
            coordShip.add(spaceship.getX());
            coordShip.add(spaceship.getY());
        }
        else if (m instanceof GameConfig) {
            GameConfig conf = (GameConfig)m;
            this.alien_image_path = conf.getAlien_image();
            this.bullet_image_path = conf.getBullet_image();
            this.ship_image_path = conf.getSpaceship_image();
            this.background_image_path = conf.getBackground_image();

            this.setImages();
        }

        this.repaint();
    }
}