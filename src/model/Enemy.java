package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;

public class Enemy extends GameObject {

    private BufferedImage sprite;

    // movement
    private int dx = 0;
    private int dy = 0;
    private final int speed = 2;

    // random movement control
    private int moveTimer = 0;
    private static final int CHANGE_INTERVAL = 30; // frames
    private final Random rand = new Random();

    public Enemy(int x, int y) {
        super(x, y, 32, 32);

        try {
            sprite = ImageIO.read(getClass().getResource("zombie.png"));
        } catch (Exception e) {
            System.err.println("Enemy sprite missing. Using placeholder.");
        }
    } // Enemy
    

    @Override
    public void update(GameModel model) {
        moveTimer++;


        if (moveTimer >= CHANGE_INTERVAL) {
            moveTimer = 0;

            dx = 0;
            dy = 0;

            int dir = rand.nextInt(4);
            switch (dir) {
                case 0 -> dx = speed;   
                case 1 -> dx = -speed;  
                case 2 -> dy = speed;   
                case 3 -> dy = -speed;  
            }
        }

        x += dx;
        y += dy;

        // 保证 enemy 不出世界边界
        x = Math.max(0, Math.min(x, model.getWorldWidth() - w));
        y = Math.max(0, Math.min(y, model.getWorldHeight() - h));
    }

    @Override
    public void drawOn(Graphics2D g2) {
        g2.drawImage(sprite, x, y, w, h, null);
    }
}

