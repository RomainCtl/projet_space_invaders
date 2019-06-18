package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import controller.SpaceInvader;
import model.Alien;

public class ViewAlienArmy extends JPanel implements Observer {
    private static final long serialVersionUID = 8725822874882570485L;

    public String image_path = "../assets/alien.gif";

    private Image img;
    private Vector<Vector<Double>> coord;

    public ViewAlienArmy() {
        URL img_url = this.getClass().getResource(this.image_path);
        if (img_url == null) {
            System.err.println("Impossible de trouver l'image d'alien ! : "+img_url);
            throw new ExceptionInInitializerError();
        }
        else {
            coord = new Vector<Vector<Double>>();
            ImageIcon ii = new ImageIcon(img_url);
            img = ii.getImage();
        }
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        // for (Vector<Double> c : this.coord) {
        //     g2d.drawImage(
        //         img, c.get(0).intValue(), c.get(1).intValue(), this
        //     );
        // }
        g2d.drawImage(img, 10, 50, this);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    @Override
    public void update(Observable m, Object o) {
        coord = new Vector<Vector<Double>>();

        ArrayList<Alien> aliens = ((SpaceInvader) m).getAliens();
        for (Alien a : aliens) {
            Vector<Double> tmp = new Vector<Double>();
            tmp.add(a.getX()); // x
            tmp.add(a.getY()); // y

            coord.add(tmp);
        }
        this.repaint();
    }
}