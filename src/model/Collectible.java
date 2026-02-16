package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Collectible extends GameObject {
	
	private BufferedImage sprite;
	private boolean collected = false;
	
	public Collectible(int x, int y) {
		super(x, y, 16, 16);
		
		try {
            sprite = ImageIO.read(
                getClass().getResource("gold.png")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public Collectible(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		try {
			sprite = ImageIO.read(
					getClass().getResource("gold.png")
					);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameModel model) {
		// TODO Auto-generated method stub
	}

	@Override
	public void drawOn(Graphics2D g2) {
        if (!collected) { g2.drawImage(sprite, x, y, w, h, null); }		
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected() {
		this.collected = true;
	}

}
