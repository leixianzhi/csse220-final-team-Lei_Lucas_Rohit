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

    // ===== level system =====
    private int currentLevel = 1;
    private final int maxLevel;

    // Defer level switching to AFTER the update loop to avoid ConcurrentModificationException
    private boolean pendingAdvance = false;

    // Final win (after last level)
    private boolean gameWon = false;

    public GameModel(int worldWidth, int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        this.maxLevel = detectMaxLevel();
        loadLevel(currentLevel);
    }

    public void updateAll() {
        if (gameWon) return;

        // Check if all collectibles collected -> open exit
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

        // Iterate over a snapshot so objects can be rebuilt later safely
        ArrayList<GameObject> snapshot = new ArrayList<>(objects);
        for (GameObject obj : snapshot) {
            obj.update(this);
        }

        // After everyone updates, handle any requested level advance
        if (pendingAdvance) {
            pendingAdvance = false;
            advanceLevelOrWin();
        }
    }

    /** Player calls this when it touches an OPEN exit. (Does NOT switch immediately.) */
    public void requestAdvanceLevel() {
        // If already requested, do nothing (prevents repeated requests while overlapping exit)
        if (!pendingAdvance) {
            pendingAdvance = true;
        }
    }

    private void advanceLevelOrWin() {
        if (currentLevel < maxLevel) {
            currentLevel++;
            loadLevel(currentLevel);
        } else {
            gameWon = true;
        }
    }

    /** Clears and rebuilds level objects from levelN.txt. Keeps the same Player instance across levels. */
    private void loadLevel(int levelNum) {
        // Clear old level data (keep player reference, but we'll reposition it)
        objects.clear();
        enemies.clear();
        collectibles.clear();
        walls.clear();
        nextLevel = null;

        buildLevel(levelNum);

        // Rebuild draw/update list
        if (player != null) objects.add(player);
        objects.addAll(enemies);
        objects.addAll(collectibles);
        objects.addAll(walls);
        if (nextLevel != null) objects.add(nextLevel);
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
                    } else if (tile == 'E') {
                        enemies.add(new Enemy(
                                col * TILE_WIDTH + TILE_WIDTH / 4,
                                row * TILE_HEIGHT + TILE_HEIGHT / 4,
                                TILE_WIDTH / 2,
                                TILE_HEIGHT / 2
                        ));
                    } else if (tile == 'o') {
                        collectibles.add(new Collectible(
                                col * TILE_WIDTH + TILE_WIDTH / 4,
                                row * TILE_HEIGHT + TILE_HEIGHT / 4,
                                TILE_WIDTH / 2,
                                TILE_HEIGHT / 2
                        ));
                    } else if (tile == 'P') {
                        int px = col * TILE_WIDTH + TILE_WIDTH / 4;
                        int py = row * TILE_HEIGHT + TILE_HEIGHT / 4;
                        int pw = TILE_WIDTH / 2;
                        int ph = TILE_HEIGHT / 2;

                        // Keep player across levels so score/lives persist
                        if (player == null) {
                            player = new Player(px, py, pw, ph);
                        } else {
                            player.setPosition(px, py);
                            player.setSize(pw, ph);
                        }
                    } else if (tile == 'N') {
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
            System.out.println("Level file not found: " + levelFile.getName());
        }
    }

    /** Detects max level by checking for level1.txt, level2.txt, ... */
    private int detectMaxLevel() {
        int n = 1;
        while (new File("level" + n + ".txt").exists()) {
            n++;
        }
        return Math.max(1, n - 1);
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

    public int getCurrentLevel() { return currentLevel; }
    public int getMaxLevel() { return maxLevel; }
}
