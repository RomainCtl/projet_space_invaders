package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

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

public class MainInterface extends JFrame implements Observer, KeyListener {

    private static final long serialVersionUID = 4551724583146448623L;
    private JPanel game_area;
    private JPanel info_area;
    private JMenuBar menu_bar;

    private SpaceInvader controller;

    private ViewAlienArmy alienarmy;

    private JLabel nb_kill;
    private JLabel nb_bullet;
    private JLabel ratio;

    public static int GAME_W = 600;
    public static int GAME_H = 600;

    public MainInterface(SpaceInvader instance) {
        this.controller = instance;

        this.menu_bar = new JMenuBar();
        this.setMenuBar();

        this.initInterface();

        this.addKeyListener(this);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Space Invader");
        this.setBounds(100, 100, 750, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void initInterface() {
        // game area
        this.game_area = new JPanel();
        this.game_area.setBackground(Color.BLACK);
        this.game_area.setSize(MainInterface.GAME_W, MainInterface.GAME_H);

        // TODO
        this.alienarmy = new ViewAlienArmy();
        this.controller.addObserver(this.alienarmy);
        // this.game_area.add(this.alienarmy);

        this.add(this.game_area);

        // informations area
        this.info_area = new JPanel();
        this.info_area.setLayout(new BoxLayout(this.info_area, BoxLayout.Y_AXIS));
        this.info_area.setBorder(new EmptyBorder(0, 600, 0, 0));

        this.nb_kill = new JLabel("Kill: 0");
        this.nb_bullet = new JLabel("Bullet send: 0");
        this.ratio = new JLabel("Ratio: 1");

        this.info_area.add(this.nb_kill);
        this.info_area.add(Box.createRigidArea(new Dimension(0, 15)));
        this.info_area.add(this.nb_bullet);
        this.info_area.add(Box.createRigidArea(new Dimension(0, 15)));
        this.info_area.add(this.ratio);

        this.add(this.info_area);
    }

    private void setMenuBar() {
        JMenuItem new_game = new JMenuItem("Nouvelle Partie");
        new_game.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                controller.restart();
                initInterface();
            }
        });
        this.menu_bar.add(new_game);

        JMenuItem pause = new JMenuItem("Pause");
        pause.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                controller.setPause();
            }
        });
		this.menu_bar.add(pause);

        JMenuItem exit = new JMenuItem("Quitter");
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        this.menu_bar.add(exit);

        this.setJMenuBar(this.menu_bar);
    }

    @Override
    public void update(Observable m, Object o) {
        int kill = ((Game) m).getNbKill();
        int bullet = ((Game) m).getNbBulletSend();
        double ratio = ((Game) m).getRatio();

        this.nb_kill.setText("Kill: "+kill);
        this.nb_bullet.setText("Bullet send: "+bullet);
        this.ratio.setText("Ratio: "+ratio);
    }

    // Key events
    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyChar() == KeyEvent.VK_P)
            this.controller.setPause();
        if (evt.getKeyChar() == KeyEvent.VK_SPACE)
            this.controller.sendBullet();
        if (evt.getKeyChar() == KeyEvent.VK_RIGHT)
            this.controller.shipMove(Entity.RIGHT);
        if (evt.getKeyChar() == KeyEvent.VK_LEFT)
            this.controller.shipMove(Entity.LEFT);
    }

    @Override
    public void keyReleased(KeyEvent evt) { }

    @Override
    public void keyTyped(KeyEvent evt) { }
}