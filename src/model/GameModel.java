package model;

import java.util.ArrayList;
import java.util.List;

import ui.Wall;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private final int worldWidth;
    private final int worldHeight;

    private final List<GameObject> objects = new ArrayList<>();
    private final Player player;

    public GameModel(int worldWidth, int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        player = new Player(50, 50);
        objects.add(player);

        objects.add(new Enemy(300, 200));

       
        objects.add(new Wall(200, 300, 200, 20));
    }

    public void updateAll() {
        for (GameObject obj : objects) {
            obj.update(this);
        }
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }
}
