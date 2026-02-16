package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ui.Wall;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private final int worldWidth;
    private final int worldHeight;

    private final List<GameObject> objects = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Collectible> collectibles = new ArrayList<>();
    private final List<Wall> walls = new ArrayList<>();
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
       
//        walls.add(new Wall(200, 300, 200, 20));
		File lvl1 = new File("level1.txt");
		buildLevel(lvl1);
        
        for (Enemy enemy : enemies) {
			objects.add(enemy);	// add pointers to enemies in objects, allowing iteration over them separately
		}
        for (Collectible collectible : collectibles) {
        	objects.add(collectible);
        }
        for (Wall wall : walls) {
        	objects.add(wall);
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
    
    public List<Wall> getWalls() {
    	return walls;
    }
    
    public int getWorldWidth() {
        return worldWidth;
    }

    public int getWorldHeight() {
        return worldHeight;
    }
    
    private void buildLevel(File levelFile) {
    	
    	ArrayList<String> lines = new ArrayList<>();
    	int levelWidth = 0;
    	int levelHeight = 0;
    	
    	try {
			Scanner dimensionScanner = new Scanner(levelFile);
			for (String line = dimensionScanner.nextLine(); dimensionScanner.hasNextLine(); line = dimensionScanner.nextLine()) {
				levelWidth = Math.max(levelWidth, line.length());
				levelHeight++;
				lines.add(line);
			}
			dimensionScanner.close();
			
			levelHeight++;
			
			final int TILE_WIDTH = worldWidth/levelWidth;
			final int TILE_HEIGHT = worldHeight/levelHeight;
			
			for (int row = 0; row < lines.size(); row++) {
				for (int col = 0; col < lines.get(row).length(); col++) {
				    char tile = lines.get(row).charAt(col);
				    if (tile == '*') {						
				    	walls.add(new Wall(col * TILE_WIDTH, row * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT));
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Exception: Level File Not Found");
		}
    }
}
