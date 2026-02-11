package ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.Timer;

import model.GameModel;
import model.GameObject;

public class GameComponent extends JComponent {

    private final GameModel model;
    private final Timer timer;

    public GameComponent(GameModel model) {
        this.model = model;
        setPreferredSize(new Dimension(
                model.getWorldWidth(),
                model.getWorldHeight()
        ));

        timer = new Timer(16, e -> {
            model.updateAll();
            repaint();
        });
        timer.start();
    }
    
    private void printHUD(Graphics2D g2d) {
		
		Font ogFont = g2d.getFont();
		
		g2d.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		
		g2d.drawString("Lives: " + model.getPlayer().getLives(), 20, 30);
		g2d.drawString("Score: " + model.getPlayer().getScore(), 20, 60);
		
		g2d.setFont(ogFont);	// preserves font for future drawings
		
	}
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (GameObject obj : model.getObjects()) {
            obj.drawOn(g2);
        }
        
        printHUD(g2);
        
    }
}

