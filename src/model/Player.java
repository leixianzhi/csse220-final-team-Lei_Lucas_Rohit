package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import ui.Wall;

public class Player extends GameObject {

    private int lives = 3;
    private int score = 0;
    private final int speed = 4;

    private boolean left, right, up, down;
    private boolean hitLastFrame = false;

    private BufferedImage sprite;

    public Player(int x, int y) {
        super(x, y, 32, 32);

        try {
            sprite = ImageIO.read(getClass().getResource("steve.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);

        try {
            sprite = ImageIO.read(getClass().getResource("steve.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLeft(boolean v)  { left = v; }
    public void setRight(boolean v) { right = v; }
    public void setUp(boolean v)    { up = v; }
    public void setDown(boolean v)  { down = v; }

    public int getLives() { return lives; }
    public int getScore() { return score; }

    @Override
    public void update(GameModel model) {

        if (model.isGameWon()) return;

        if (left)  moveX(-speed, model);
        if (right) moveX(speed, model);
        if (up)    moveY(-speed, model);
        if (down)  moveY(speed, model);

        x = Math.max(0, Math.min(x, model.getWorldWidth() - w));
        y = Math.max(0, Math.min(y, model.getWorldHeight() - h));

        boolean hitThisFrame = false;

        for (Enemy enemy : model.getEnemies()) {
            if (getBounds().intersects(enemy.getBounds())) {
                hitThisFrame = true;
                break;
            }
        }

        for (Collectible collectible : model.getCollectibles()) {
            if (!collectible.isCollected() &&
                    getBounds().intersects(collectible.getBounds())) {
                score++;
                collectible.setCollected();
            }
        }

        if (hitThisFrame && !hitLastFrame) {
            lives--;
        }

        hitLastFrame = hitThisFrame;

        // EXIT: request next level (deferred; avoids ConcurrentModificationException)
        if (model.getNextLevel() != null &&
                model.getNextLevel().isOpen() &&
                getBounds().intersects(model.getNextLevel().getBounds())) {

            model.requestAdvanceLevel();
        }
    }

    private void moveX(int amount, GameModel model) {
        x += amount;
        for (Wall wall : model.getWalls()) {
            if (getBounds().intersects(wall.getBounds())) {
                x -= amount;
                break;
            }
        }
    }

    private void moveY(int amount, GameModel model) {
        y += amount;
        for (Wall wall : model.getWalls()) {
            if (getBounds().intersects(wall.getBounds())) {
                y -= amount;
                break;
            }
        }
    }

    @Override
    public void drawOn(Graphics2D g2) {
        g2.drawImage(sprite, x, y, w, h, null);
    }
}
