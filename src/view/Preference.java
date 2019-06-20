package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import model.GameConfig;
import model.enums.BulletSpeed;
import model.enums.Level;
import util.JNumberTextField;

public class Preference extends JFrame {
    private static final long serialVersionUID = -4171082018534059578L;

    private GameConfig config;

    public Preference(GameConfig conf) {
        this.config = conf;
        // init
        this.setLayout(new GridLayout(5, 4));

        this.add(new JLabel("Vitesse des aliens : "));
        JList input_speed = new JList<String>(Level.names());
        input_speed.setSelectedIndex(this.config.getAlien_speed().ordinal());
        this.add(input_speed);

        this.add(new JLabel("Vitesse des missiles : "));
        JList input_bullet = new JList<String>(BulletSpeed.names());
        input_bullet.setSelectedIndex(this.config.getBullet_interval().ordinal());
        this.add(input_bullet);

        this.add(new JLabel("Nombres d'aliens par lignes : "));
        JNumberTextField input_row = new JNumberTextField(String.valueOf(this.config.getNumber_alien_row()));
        this.add(input_row);

        this.add(new JLabel("Nombres d'aliens par colonnes : "));
        JNumberTextField input_col = new JNumberTextField(String.valueOf(this.config.getNumber_alien_col()));
        this.add(input_col);

        this.add(new JLabel("Sprite alien :"));
        JTextField alien = new JTextField(this.config.getAlien_image());
        this.add(alien);

        this.add(new JLabel("Background image :"));
        JTextField background = new JTextField(this.config.getBackground_image());
        this.add(background);

        this.add(new JLabel("Sprite Bullet :"));
        JTextField bullet = new JTextField(this.config.getBullet_image());
        this.add(bullet);

        this.add(new JLabel("Sprite spaceship :"));
        JTextField ship = new JTextField(this.config.getSpaceship_image());
        this.add(ship);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(cancel);

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setAlien_image(alien.getText());
                config.setAlien_speed(Level.valueOf((String)input_speed.getSelectedValue()));
                config.setBackground_image(background.getText());
                config.setBullet_interval(BulletSpeed.valueOf((String)input_bullet.getSelectedValue()));
                config.setBullet_image(bullet.getText());
                config.setNumber_alien_col(input_row.getNumber());
                config.setNumber_alien_row(input_col.getNumber());
                config.setSpaceship_image(ship.getText());

                dispose();
            }
        });
        this.add(new JLabel());
        this.add(new JLabel());
        this.add(save);


        // own settings
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Space Invader - Preferences");
        this.setBounds(200, 200, 800, 300);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}