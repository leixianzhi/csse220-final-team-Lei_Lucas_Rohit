package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ui.Wall;

public class GameModel {

    private final int worldWidth;
    private final int worldHeight;

    private final List<GameObject> objects = new ArrayList<>();
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Collectible> collectibles = new ArrayList<>();
    private final List<Wall> walls = new ArrayList<>();

    private Player player;
    private NextLevel nextLevel;

    private boolean gameWon = false;

    public GameModel(int worldWidth, int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        buildLevel(1);

        objects.add(player);

        for (Enemy enemy : enemies) {
            objects.add(enemy);
        }

        for (Collectible collectible : collectibles) {
            objects.add(collectible);
        }

        for (Wall wall : walls) {
            objects.add(wall);
        }

        if (nextLevel != null) {
            objects.add(nextLevel);
        }
    }

    public void updateAll() {

        if (gameWon) return;

        // Check if all collectibles collected
        boolean allCollected = true;
        for (Collectible c : collectibles) {
            if (!c.isCollected()) {
                allCollected = false;
                break;
            }
        }

        if (allCollected && nextLevel != null) {
            nextLevel.setOpen(true);
        }

        for (GameObject obj : objects) {
            obj.update(this);
        }
    }

    private void buildLevel(int levelNum) {
    	
    	File levelFile = new File("level" + levelNum + ".txt");

        ArrayList<String> lines = new ArrayList<>();
        int levelWidth = 0;
        int levelHeight = 0;

        try {
            Scanner scanner = new Scanner(levelFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                levelWidth = Math.max(levelWidth, line.length());
                levelHeight++;
                lines.add(line);
            }
            scanner.close();

            final int TILE_WIDTH = worldWidth / levelWidth;
            final int TILE_HEIGHT = worldHeight / levelHeight;

            for (int row = 0; row < lines.size(); row++) {
                for (int col = 0; col < lines.get(row).length(); col++) {

                    char tile = lines.get(row).charAt(col);

                    if (tile == '*') {
                        walls.add(new Wall(
                                col * TILE_WIDTH,
                                row * TILE_HEIGHT,
                                TILE_WIDTH,
                                TILE_HEIGHT
                        ));
                    }
                    else if (tile == 'E') {
                        enemies.add(new Enemy(
                                col * TILE_WIDTH + TILE_WIDTH / 4,
                                row * TILE_HEIGHT + TILE_HEIGHT / 4,
                                TILE_WIDTH / 2,
                                TILE_HEIGHT / 2
                        ));
                    }
                    else if (tile == 'o') {
                        collectibles.add(new Collectible(
                                col * TILE_WIDTH + TILE_WIDTH / 4,
                                row * TILE_HEIGHT + TILE_HEIGHT / 4,
                                TILE_WIDTH / 2,
                                TILE_HEIGHT / 2
                        ));
                    }
                    else if (tile == 'P') {
                        player = new Player(
                                col * TILE_WIDTH + TILE_WIDTH / 4,
                                row * TILE_HEIGHT + TILE_HEIGHT / 4,
                                TILE_WIDTH / 2,
                                TILE_HEIGHT / 2
                        );
                    }
                    else if (tile == 'N') {
                        nextLevel = new NextLevel(
                                col * TILE_WIDTH,
                                row * TILE_HEIGHT,
                                TILE_WIDTH,
                                TILE_HEIGHT
                        );
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Level file not found.");
        }
    }

    public List<GameObject> getObjects() { return objects; }
    public Player getPlayer() { return player; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Collectible> getCollectibles() { return collectibles; }
    public List<Wall> getWalls() { return walls; }

    public NextLevel getNextLevel() { return nextLevel; }

    public int getWorldWidth() { return worldWidth; }
    public int getWorldHeight() { return worldHeight; }

    public boolean isGameWon() { return gameWon; }
    public void setGameWon() { gameWon = true; }
}
