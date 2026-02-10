package model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Collectible extends GameObject {
	
	private BufferedImage sprite;
	
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

	@Override
	public void update(GameModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawOn(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}

}
