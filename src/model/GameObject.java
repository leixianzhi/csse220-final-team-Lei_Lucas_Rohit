package model;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject {
    protected int x, y, w, h;

    public GameObject(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

    public abstract void update(GameModel model);

    public abstract void drawOn(Graphics2D g2);
}
//