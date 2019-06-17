package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import model.Game;

public class MainInterface extends JFrame implements Observer {

    private JPanel game_area;
    private JPanel info_area;
    private JMenuBar menu_bar;

    private JLabel nb_kill;
    private JLabel nb_bullet;
    private JLabel ratio;

    private static int GAME_W = 700;
    private static int GAME_H = 700;

    public MainInterface() {
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Space Invader");

        this.menu_bar = new JMenuBar();
        this.setMenuBar();

        this.initInterface();

        this.setBounds(100, 100, 1000, 750);
        this.setResizable(false);
        this.setVisible(true);
    }

    private void initInterface() {
        this.game_area = new JPanel();
        this.info_area = new JPanel();

        // TODO
        this.nb_kill = new JLabel("Kill: 0");
        this.nb_bullet = new JLabel("Bullet send: 0");
        this.ratio = new JLabel("Ratio: 1");

        this.add(this.game_area, BorderLayout.WEST);
        this.add(this.info_area, BorderLayout.EAST);
    }

    private void setMenuBar() {
        JMenuItem new_game = new JMenuItem("Nouvelle Partie");
        new_game.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                // TODO send to controler (reinit Game()) ...
                //initInterface();
            }
        });
        this.menu_bar.add(new_game);

        JMenuItem pause = new JMenuItem("Pause");
        pause.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                // TODO send to controler pause ...
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
    }

    @Override
    public void update(Observable m, Object o) {
        int kill = ((Game) m).getNbKill();
        int bullet = ((Game) m).getNbBulletSend();
        double ratio = ((Game) m).getRatio();

        this.nb_kill.setText("Kill: "+kill);
        this.nb_bullet.setText("Bullet send: "+bullet);
        this.ratio.setText("Ratio: "+ratio);

        // Other ?
    }
}