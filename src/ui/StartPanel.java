package ui;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {

    public StartPanel(Runnable onStart) {

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // Title
        JLabel title = new JLabel("CSSE 220 Adventure", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 40));
        title.setForeground(Color.WHITE);

        // Start Button
        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));

        // When button is clicked, run the provided action
        startButton.addActionListener(e -> onStart.run());

        add(title, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);
    }
}

//jj