package ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import model.GameModel;
import model.GameObject;

public class Wall extends GameObject {

    private BufferedImage sprite;

    public Wall(int x, int y, int w, int h) {
        super(x, y, w, h);

        try {
            sprite = ImageIO.read(
                getClass().getResource("wall.jpg")
            );
        } catch (Exception e) {
            System.err.println("Wall sprite not found.");
            e.printStackTrace();
        }
    }

    @Override
    public void update(GameModel model) {
    }

    @Override
    public void drawOn(Graphics2D g2) {
        if (sprite != null) {
            g2.drawImage(sprite, x, y, w, h, null);
        }
    }
}
