package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JComponent;

public class viewSpaceship extends JPanel implements Observer{

    private space_ship_image;
    private x;
    private y;

    public viewSpaceship() {
        ImageIcon space_ship = new ImageIcon(this.getClass().getResource("../../assets/ship.gif"));
        space_ship_img = space_ship.getImage();
    }

    public void update(Observable m, Object o) {
        this.x = ((Entity) m.getX());
        this.y = ((Entity) m.getY())
    }

    private void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(space_ship_image, x, y, this);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

}