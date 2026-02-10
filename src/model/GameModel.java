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
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Collectible> collectibles = new ArrayList<>();
    private final Player player;

    public GameModel(int worldWidth, int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        player = new Player(50, 50);
        objects.add(player);

        enemies.add(new Enemy(300, 200));
        // place enemies in list of enemies
        
        collectibles.add(new Collectible(100, 100));
        collectibles.add(new Collectible(200, 100));
       
        objects.add(new Wall(200, 300, 200, 20));
        
        for (Enemy enemy : enemies) {
			objects.add(enemy);	// add pointers to enemies in objects, allowing iteration over them separately
		}
        for (Collectible collectible : collectibles) {
        	objects.add(collectible);
        }
        
        
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
    
    public List<Enemy> getEnemies() {
    	return enemies;
    }
    
    public List<Collectible> getCollectibles() {
    	return collectibles;
    }
    
    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }
}
