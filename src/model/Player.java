package model;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Player extends GameObject {

    private final int speed = 4;
    private boolean left, right, up, down;
    private BufferedImage sprite;

    public Player(int x, int y) {
        super(x, y, 32, 32);

        try {
            sprite = ImageIO.read(
                getClass().getResource("steve.png")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLeft(boolean v)  { left = v; }
    public void setRight(boolean v) { right = v; }
    public void setUp(boolean v)    { up = v; }
    public void setDown(boolean v)  { down = v; }

    @Override
    public void update(GameModel model) {
        if (left)  x -= speed;
        if (right) x += speed;
        if (up)    y -= speed;
        if (down)  y += speed;

        x = Math.max(0, Math.min(x, model.getWorldWidth() - w));
        y = Math.max(0, Math.min(y, model.getWorldHeight() - h));
    }

    @Override
    public void drawOn(Graphics2D g2) {
        g2.drawImage(sprite, x, y, w, h, null);
    }
}
