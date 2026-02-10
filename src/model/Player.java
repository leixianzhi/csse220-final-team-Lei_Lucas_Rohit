package model;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

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
    
    public int getLives() { return lives; }
    public int getScore() { return score; }
    
    @Override
    public void update(GameModel model) {
        if (left)  x -= speed;
        if (right) x += speed;
        if (up)    y -= speed;
        if (down)  y += speed;

        x = Math.max(0, Math.min(x, model.getWorldWidth() - w));
        y = Math.max(0, Math.min(y, model.getWorldHeight() - h));
        
        
        boolean hitThisFrame = false;
        
        for (Enemy enemy : model.getEnemies()) {
			if (this.getBounds().intersects(enemy.getBounds())) {
				hitThisFrame = true;
				break;
			}
		}
        
        // if player touches enemy this frame but not last frame, lose a life.
        if (hitThisFrame && !hitLastFrame) { lives--; }
        
        hitLastFrame = hitThisFrame;
        
    }

    @Override
    public void drawOn(Graphics2D g2) {
        g2.drawImage(sprite, x, y, w, h, null);
    }
}
