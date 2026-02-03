package ui;

import javax.swing.JFrame;

import model.GameModel;

import javax.swing.JFrame;


public class GameWindow {

    public static void show() {

        GameModel model = new GameModel(600, 600);
        GameComponent view = new GameComponent(model);

        JFrame frame = new JFrame("CSSE220 Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);

        GameController controller = new GameController(model.getPlayer());
        frame.addKeyListener(controller);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        view.setFocusable(true);
        view.requestFocusInWindow();
    }
}