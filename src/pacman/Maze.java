package pacman;



import javafx.scene.Group;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Maze {

    private Set<BarObstacle> obstacles;
    Set<Cookie> cookies;

    Maze() {
        obstacles = new HashSet<>();
        cookies = new HashSet<>();
    }

    /**
     * Checks if point is touching obstacles
     * @param x
     * @param y
     * @return
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
     * lets you know if there's an obstacle in the current coordinate
     * @param fromX
     * @param toX
     * @param fromY
     * @param toY
     * @return
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
     * Draws the maze
     * @param root
     */
    public void CreateMaze(Group root) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("maze1.map"));
            String line;
            int y = 0;
            while ((line = br.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    if (line.charAt(x) == '1') {
                        this.obstacles.add(new BarObstacle(x, y));
                    } else if (line.charAt(x) == '2') {
                        Cookie cookie = new Cookie((x+0.5) * BarObstacle.THICKNESS, (y+0.5) * BarObstacle.THICKNESS);
                        this.cookies.add(cookie);
                        root.getChildren().add(cookie);
                    }
                }
                y++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.getChildren().addAll(obstacles);
    }
}
