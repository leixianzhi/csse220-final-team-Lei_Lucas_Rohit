package ui;

import java.awt.Graphics2D;

import model.GameModel;
import model.GameObject;

public class Wall extends GameObject {

    public Wall(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    @Override
    public void update(GameModel model) {
   
    }

    @Override
    public void drawOn(Graphics2D g2) {
        g2.drawRect(x, y, w, h);
    }
}
