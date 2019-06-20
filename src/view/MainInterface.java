package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.SpaceInvader;
import model.Entity;
import model.Game;
import model.GameConfig;

public class MainInterface extends JFrame implements Observer, KeyListener {
    private static final long serialVersionUID = 4551724583146448623L;

    // Set of currently pressed keys
    private final Set<Integer> pressed = new HashSet<Integer>();

    private GameArea game_area;
    private JPanel info_area;
    private JMenuBar menu_bar;;

    private SpaceInvader controller;
    private GameConfig config;

    private JLabel nb_kill;
    private JLabel nb_bullet;
    private JLabel ratio;
    private JLabel nb_enemies;
    private JLabel vague;

    public static int GAME_W = 600;
    public static int GAME_H = 600;

    public MainInterface(SpaceInvader instance, GameConfig conf) {
        this.controller = instance;
        this.config = conf;

        // bar de menu
        this.menu_bar = new JMenuBar();
        this.setMenuBar();

        // key events
        this.addKeyListener(this);

        // own settings
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Space Invader");
        this.setBounds(100, 100, 750, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void initInterface(int nb_e) {
        // game area
        this.game_area = new GameArea(this.config);
        this.controller.addObserver(this.game_area);
        this.add(this.game_area);

        // informations area (do not need sub class)
        this.info_area = new JPanel();
        this.info_area.setLayout(new BoxLayout(this.info_area, BoxLayout.Y_AXIS));
        this.info_area.setSize(150, 600);
        this.info_area.setBorder(new EmptyBorder(0, 600, 0, 0));

        this.nb_kill = new JLabel("Kill: 0");
        this.nb_bullet = new JLabel("Bullet send: 0");
        this.ratio = new JLabel("Ratio: 1");
        this.nb_enemies = new JLabel("Enemies: "+nb_e);
        this.vague = new JLabel("Vague: 1");

        this.info_area.add(this.nb_kill);
        this.info_area.add(Box.createRigidArea(new Dimension(0, 15)));
        this.info_area.add(this.nb_bullet);
        this.info_area.add(Box.createRigidArea(new Dimension(0, 15)));
        this.info_area.add(this.ratio);
        this.info_area.add(Box.createRigidArea(new Dimension(0, 15)));
        this.info_area.add(this.nb_enemies);
        this.info_area.add(Box.createRigidArea(new Dimension(0, 15)));
        this.info_area.add(this.vague);

        this.add(this.info_area);
    }

    private void setMenuBar() {
        // restart game
        JMenuItem new_game = new JMenuItem("Nouvelle Partie");
        new_game.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                controller.restart();
            }
        });
        this.menu_bar.add(new_game);

        // set pause (can use 'p' key too)
        JMenuItem pause = new JMenuItem("Pause");
        pause.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                controller.setPause();
            }
        });
        this.menu_bar.add(pause);

        JMenuItem preference = new JMenuItem("Preferences");
        preference.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                controller.setPause(true);
                Preference p = new Preference(config);
            }
        });
        this.menu_bar.add(preference);

        // exit game
        JMenuItem exit = new JMenuItem("Quitter");
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        this.menu_bar.add(exit);

        // set this bar to main bar
        this.setJMenuBar(this.menu_bar);
    }

    // update data from 'Game' model
    @Override
    public void update(Observable m, Object o) {
        int kill = ((Game) m).getNbKill();
        int remain_e = ((Game) m).getNbRemainingEnemies();
        int bullet = ((Game) m).getNbBulletSend();
        double ratio = ((Game) m).getRatio();
        int nb_alien_by_army = ((Game) m).getNbAlienByArmy();

        this.nb_kill.setText("Kill: "+kill);
        this.nb_bullet.setText("Bullet send: "+bullet);
        this.ratio.setText("Ratio: "+new DecimalFormat("#.##").format(ratio));
        this.nb_enemies.setText("Enemies: "+remain_e);
        this.vague.setText("Vague: "+ (kill+remain_e)/nb_alien_by_army);
    }

    // Key events
    @Override
    public void keyPressed(KeyEvent evt) {
        pressed.add(evt.getKeyCode());
        for (Integer key_code : pressed) {
            if (key_code == KeyEvent.VK_P)
                this.controller.setPause();
            if (key_code == KeyEvent.VK_SPACE)
                this.controller.sendBullet();
            if (key_code == KeyEvent.VK_RIGHT)
                this.controller.shipMove(Entity.RIGHT);
            if (key_code == KeyEvent.VK_LEFT)
                this.controller.shipMove(Entity.LEFT);
        }
    }
    @Override
    public void keyReleased(KeyEvent evt) {
        pressed.remove(evt.getKeyCode());
    }
    @Override
    public void keyTyped(KeyEvent evt) { }
}