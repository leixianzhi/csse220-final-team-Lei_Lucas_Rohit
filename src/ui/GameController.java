package ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import model.Player;

public class GameController extends KeyAdapter {

    private final Player player;

    public GameController(Player player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT  -> player.setLeft(true);
            case KeyEvent.VK_RIGHT -> player.setRight(true);
            case KeyEvent.VK_UP    -> player.setUp(true);
            case KeyEvent.VK_DOWN  -> player.setDown(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT  -> player.setLeft(false);
            case KeyEvent.VK_RIGHT -> player.setRight(false);
            case KeyEvent.VK_UP    -> player.setUp(false);
            case KeyEvent.VK_DOWN  -> player.setDown(false);
        }
    }
}
