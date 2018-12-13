package pacman.models.maze;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pacman.GameManager;
import pacman.models.characters.Ghost;
import pacman.models.characters.Pacman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Model for the maze and organising its components.
 */
public class Maze {

    private Set<BarObstacle> obstacles;
    private Set<Cookie> cookies;
    private Set<Ghost> ghosts;
    private Pacman pacman;
    private Color barColor;
    private int width;
    private int height;
    private File mazeFile;
    private String mazeFileName;
    private boolean customMapLoaded;

    /**
     * Constructor for the maze initializing default values.
     *
     * @param barColor The color of the obstacles in the maze.
     */
    public Maze(Color barColor) {
        obstacles = new HashSet<>();
        cookies = new HashSet<>();
        ghosts = new HashSet<>();
        this.barColor = barColor;
        mazeFile = new File("src/pacman/resources/defaultmaze.map");
        mazeFileName = "Default";
        customMapLoaded = false;
    }

    /**
     * Checks if point is touching obstacles
     *
     * @param x The x-position to check.
     * @param y The y-position to check.
     * @return Whether or not the given coordinate is touching an obstacle.
     */
    public Boolean isTouching(double x, double y, double padding) {
        for (BarObstacle barObstacle:obstacles) {
            if (
                x >= barObstacle.getX() - padding &&
                x <= barObstacle.getX() + padding + barObstacle.getWidth() &&
                y >= barObstacle.getY() - padding &&
                y <= barObstacle.getY() + padding + barObstacle.getHeight())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is an obstacle in the current coordinate range.
     *
     * @param fromX Lower x-position to check from.
     * @param toX Upper x-position to check to.
     * @param fromY Lower y-position to check from.
     * @param toY Upper y-position to check to.
     * @return Whether or not the given coordinate range contains an obstacle.
     */
    public Boolean hasObstacle(double fromX,  double toX, double fromY, double toY) {
        boolean isTouching = false;
        for (double i = fromX; i < toX; i++) {
            for (double j = fromY; j < toY; j++) {
                if (this.isTouching(i, j, 0)) isTouching = true;
            }
        }
        return isTouching;
    }

    /**
     * Draws the maze, based on the map file.
     *
     * @param root Pane in the scene which the maze components should be drawn onto.
     */
    public void CreateMaze(Pane root) {
        int ghostCount = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(mazeFile));
            String line;
            String prevLine = null;
            int y = 0;
            while ((line = br.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    if (line.charAt(x) == '1') {
                        this.obstacles.add(new BarObstacle(x, y, barColor));
                        if (x > 0 && line.charAt(x-1) == '1') {
                            this.obstacles.add(new BarObstacle(x-0.5, y, barColor));
                        }
                        if (y > 0 && prevLine.charAt(x) == '1') {
                            this.obstacles.add(new BarObstacle(x,y-0.5, barColor));
                            if (x > 0 && prevLine.charAt(x-1) == '1' && line.charAt(x-1) == '1') {
                                this.obstacles.add(new BarObstacle(x-0.5, y-0.5, barColor));
                            }
                        }
                    } else if (line.charAt(x) == '2') {
                        Cookie cookie = new Cookie((x * (2*BarObstacle.THICKNESS)) + 12.5, y * (2*BarObstacle.THICKNESS) + 12.5);
                        this.cookies.add(cookie);
                        root.getChildren().add(cookie);
                    } else if (line.charAt(x) == '3') {
                        Ghost ghost = new Ghost((2*x-0.5) * BarObstacle.THICKNESS, (2*y-0.5) * BarObstacle.THICKNESS, (ghostCount % 5) + 1);
                        this.ghosts.add(ghost);
                        ghostCount++;
                    } else if (line.charAt(x) == '*') {
                        this.pacman = new Pacman(2.5 * BarObstacle.THICKNESS, 2.5 * BarObstacle.THICKNESS, this, GameManager.getInstance());
                        root.getChildren().add(pacman);
                    }
                }
                y++;
                prevLine = line;
            }
            this.height = y * 2;
            root.getChildren().addAll(this.ghosts);
            if (prevLine != null) {
                this.width = prevLine.length();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.getChildren().addAll(obstacles);
        setBonusCookies(4);
    }

    /**
     * Randomly makes the specified number of cookies bonus ones.
     *
     * @param numBonusCookies The number of bonus cookies to add.
     */
    private void setBonusCookies(int numBonusCookies) {
        for (int i = 0; i < numBonusCookies; i++) {
            boolean looping = true;
            while (looping) {
                int j = 0;
                int rand = new Random().nextInt(cookies.size());
                for (Cookie cookie : cookies) {
                    if (j == rand) {
                        if (cookie.getValue() == 5) {
                            cookie.makeBonus();
                            looping = false;
                        }
                    }
                    j++;
                }
            }
        }
    }

    public void setBarColor(Color barColor) {
        this.barColor = barColor;
        for (BarObstacle obstacle:
             obstacles) {
            obstacle.setFill(barColor);
        }
    }

    public Color getBarColor() {
        return barColor;
    }

    public Set<Cookie> getCookies() {
        return cookies;
    }

    public Set<BarObstacle> getObstacles() {
        return obstacles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Set<Ghost> getGhosts() {
        return ghosts;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public void setMazeFile(File file) {
        this.mazeFile = file;
        mazeFileName = file.getName();
        customMapLoaded = true;
    }

    public String getMazeFileName() {
        if (customMapLoaded) {
            return mazeFileName;
        } else {
            return "Default";
        }
    }

    public boolean isCustomMapLoaded() {
        return customMapLoaded;
    }

    public void setCustomMapLoaded(boolean b) {
        customMapLoaded = b;
    }
}
