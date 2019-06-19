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
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.SpaceInvader;
import model.Alien;
import model.Spaceship;

public class GameArea extends JPanel implements Observer {
    private static final long serialVersionUID = 2959271263636245674L;

    public String alien_image_path = "../assets/alien.gif";
    public String bullet_image_path = "../assets/shot.gif";
    public String ship_image_path = "../assets/ship.gif";
    public String background_image_path = null; // if null, use Black color

    private Image img_alien;
    private Image img_ship;
    private Vector<Vector<Double>> coord;
    private Vector<Double> coordShip;

    public GameArea() {
        this.setBackground(Color.BLACK);
        this.setSize(MainInterface.GAME_W, MainInterface.GAME_H);

        URL img_space_url = this.getClass().getResource(this.ship_image_path);
        URL img_url = this.getClass().getResource(this.alien_image_path);
        if (img_url == null || ship_image_path == null) {
            System.err.println("Impossible de trouver les images");
            throw new ExceptionInInitializerError();
        }
        else {
            coord = new Vector<Vector<Double>>();
            ImageIcon ii1 = new ImageIcon(img_url);
            ImageIcon ii2 = new ImageIcon(img_space_url);
            img_alien = ii1.getImage();
            img_ship = ii2.getImage();
            this.repaint();
        }
    }

    // refresh interface (aliens, bullets and spaceship)
    public void paint(Graphics g) {
        super.paint(g);

        // Alien
        Graphics2D g2d = (Graphics2D) g;
        for (Vector<Double> c : this.coord) {
            g2d.drawImage(
                img_alien, c.get(0).intValue(), c.get(1).intValue(), this
            );
        }

        //Ship
        g2d.drawImage(
            img_ship, coordShip.get(0).intValue(), coordShip.get(1).intValue(), this
        );

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    // entity positions change from Model
    @Override
    public void update(Observable m, Object o) {
        coord = new Vector<Vector<Double>>();
        coordShip = new Vector<Double>();

        ArrayList<Alien> aliens = ((SpaceInvader) m).getAliens();
        for (Alien a : aliens) {
            Vector<Double> tmp = new Vector<Double>();
            tmp.add(a.getX()); // x
            tmp.add(a.getY()); // y
            coord.add(tmp);
        }

        Spaceship spaceship = ((SpaceInvader) m).getSpaceship();
        coordShip.add(spaceship.getX());
        coordShip.add(spaceship.getY());

        this.repaint();
    }
}