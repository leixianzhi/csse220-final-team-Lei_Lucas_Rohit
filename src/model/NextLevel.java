package model;

import java.awt.Color;
import java.awt.Graphics2D;

public class NextLevel extends GameObject {

    private boolean open = false;

    public NextLevel(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    public void setOpen(boolean value) {
        open = value;
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public void update(GameModel model) {
        // No movement
    }

    @Override
    public void drawOn(Graphics2D g2) {
        if (open) {
            g2.setColor(Color.GREEN);  // open
        } else {
            g2.setColor(Color.RED);    // locked
        }
        g2.fillRect(x, y, w, h);
    }
}
