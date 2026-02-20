package ui;

import javax.swing.*;
import java.awt.*;

import model.GameModel;

public class GameWindow {

    public static void show() {

        JFrame frame = new JFrame("CSSE220 Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        // ===== GAME MODEL + VIEW =====
        GameModel model = new GameModel(600, 600);
        GameComponent gameComponent = new GameComponent(model);
        GameController controller = new GameController(model.getPlayer());
        gameComponent.addKeyListener(controller);
        gameComponent.setFocusable(true);

        // ===== START PANEL =====
        StartPanel startPanel = new StartPanel(() -> {
            cardLayout.show(mainPanel, "GAME");
            gameComponent.requestFocusInWindow();
        });

        mainPanel.add(startPanel, "START");
        mainPanel.add(gameComponent, "GAME");

        frame.add(mainPanel);
        frame.setVisible(true);

        // Show start screen first
        cardLayout.show(mainPanel, "START");
    }
}
