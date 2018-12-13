package pacman.models.characters;


import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import pacman.GameManager;
import pacman.models.maze.BarObstacle;
import pacman.models.maze.Maze;

import java.util.Map;
import java.util.Random;

/**
 * Model for the Ghosts on the map.
 */
public class Ghost extends Rectangle implements Runnable, Moveable {
    private String direction;
    private GameManager gameManager = GameManager.getInstance();
    private Maze maze = gameManager.getMaze();
    private AnimationTimer animation;
    private int timesWalked;
    private double initialX;
    private double initialY;

    /**
     * Constructor initialises default values for the Ghost.
     *
     * @param x The x-position for the Ghost.
     * @param y The y-position for the Ghost.
     * @param color Integer representation of the color of the ghost (1-5).
     */
    public Ghost(double x, double y, int color) {
        this.setX(x);
        this.setY(y);
        this.initialX = x;
        this.initialY = y;
        this.setHeight(50);
        this.setWidth(50);
        Image img = new Image("/pacman/resources/ghost" + color + ".png");
        this.setFill(new ImagePattern(img));
        this.timesWalked = 0;
        this.direction = "down";
        animation = this.createAnimation(null);
    }

    /**
     * Generate a random direction (up,down,left,right)
     *
     * @param exclude1 Guaranteed to not be the generated direction.
     * @param exclude2 Guaranteed to not be the generated direction.
     * @return A random direction, that is not one of the excluded.
     */
    private String getRandomDirection(String exclude1, String exclude2) {
        String[] directions = {"left", "right", "up", "down"};
        int rnd = new Random().nextInt(directions.length);
        while (directions[rnd].equals(exclude1) || directions[rnd].equals(exclude2)) {
            rnd = new Random().nextInt(directions.length);
        }
        return directions[rnd];
    }

    /**
     * Checks if movement in the specified direction will hit an obstacle, and sets the direction if not.
     *
     * @param direction Direction of travel to check if there is a path in.
     */
    private void checkIftheresPathToGo(String direction) {
        double rightEdge, leftEdge, topEdge, bottomEdge;
        switch (direction) {
            case "down":
                leftEdge = getX() - 10;
                bottomEdge = getY() + getHeight() + 15;
                rightEdge = getX() + getWidth() + 10;
                if (!maze.hasObstacle(leftEdge, rightEdge, bottomEdge - 1, bottomEdge)) {
                    this.direction = direction;
                }
                break;
            case "up":
                leftEdge = getX() - 10;
                rightEdge = getX() + getWidth() + 10;
                topEdge = getY() - 15;
                if (!maze.hasObstacle(leftEdge, rightEdge, topEdge - 1, topEdge)) {
                    this.direction = direction;
                }
                break;
            case "left":
                leftEdge = getX() - 15;
                bottomEdge = getY() + getHeight() + 10;
                topEdge = getY() - 10;
                if (!maze.hasObstacle(leftEdge - 1, leftEdge, topEdge, bottomEdge)) {
                    this.direction = direction;
                }
                break;
            case "right":
                bottomEdge = getY() + getHeight() + 10;
                rightEdge = getX() + getWidth() + 15;
                topEdge = getY() - 10;
                if (!maze.hasObstacle(rightEdge - 1, rightEdge, topEdge, bottomEdge)) {
                    this.direction = direction;
                }
                break;
        }
    }

    /**
     * Generates random direction changes for the Ghosts.
     *
     * @param whereToGo Current direction of movement.
     * @param whereToChangeTo Direction to move if unable to continue in current direction.
     * @param leftEdge X-Position of the left edge of the Ghost.
     * @param topEdge Y-Position of the top edge of the Ghost.
     * @param rightEdge X-Position of the right edge of the Ghost.
     * @param bottomEdge Y-Position of the bottom edge of the Ghost.
     * @param padding Padding maintained between Ghost and Obstacles.
     */
    private void moveUntilYouCant(String whereToGo, String whereToChangeTo, double leftEdge, double topEdge, double rightEdge, double bottomEdge, double padding) {
        double step = 5;
        switch (whereToGo) {
            case "left":
                if (!maze.isTouching(leftEdge, topEdge, padding)) {
                    setX(leftEdge - step);
                } else {
                    while (maze.isTouching(getX(), getY(), padding)) {
                        setX(getX() + 1);
                    }
                    direction = whereToChangeTo;
                }
                break;
            case "right":
                if (!maze.isTouching(rightEdge, topEdge, padding)) {
                    setX(leftEdge + step);
                } else {
                    while (maze.isTouching(getX() + getWidth(), getY(), padding)) {
                        setX(getX() - 1);
                    }
                    direction = whereToChangeTo;
                }
                break;
            case "up":
                if (!maze.isTouching(leftEdge, topEdge, padding)) {
                    setY(topEdge - step);
                } else {
                    while (maze.isTouching(getX(), getY(), padding)) {
                        setY(getY() + 1);
                    }
                    direction = "left";
                }
                break;
            case "down":
                if (!maze.isTouching(leftEdge, bottomEdge, padding)) {
                    setY(topEdge + step);
                } else {
                    while (maze.isTouching(getX(), getY() + getHeight(), padding)) {
                        setY(getY() - 1);
                    }
                    direction = "right";
                }
                break;
        }

    }

    /**
     * Checks whether the Ghost is travelling through a doorway, and moves it to the other doorway if necessary.
     */
    public void checkDoorway() {
        double leftEdge = getX();
        double rightEdge = getX() + getWidth();
        if (rightEdge < 0) {
            setX((maze.getWidth()*2-1) * BarObstacle.THICKNESS);
        } else if (leftEdge > (maze.getWidth()*2-1) * BarObstacle.THICKNESS) {
            setX(-1 * BarObstacle.THICKNESS);
        }
    }

    /**
     * Creates the animation for the Ghost.
     *
     * @return The animation for the Ghost.
     */
    public AnimationTimer createAnimation(String s) {

        return new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if(maze.getPacman().checkGhostCoalition(maze.getGhosts()))
                    maze.getPacman().touchedGhost();
                checkDoorway();
                double leftEdge = getX();
                double topEdge = getY();
                double rightEdge = getX() + getWidth();
                double bottomEdge = getY() + getHeight();
                double padding = 12;
                timesWalked++;
                int walkAtLeast = 4;

                Map<String, String> directionsMap = Map.of(
                        "left", "down",
                        "right", "up",
                        "up", "left",
                        "down", "right"
                );

                moveUntilYouCant(direction, directionsMap.get(direction), leftEdge, topEdge, rightEdge, bottomEdge, padding);
                if (timesWalked > walkAtLeast) {
                    if (direction.equals("left") || direction.equals("right"))
                        checkIftheresPathToGo(getRandomDirection("left", "right"));
                    else
                        checkIftheresPathToGo(getRandomDirection("up", "down"));
                    timesWalked = 0;
                }

            }
        };
    }

    /**
     * Starts a new thread and begins the Ghost animation on it.
     */
    @Override
    public void run() {
        this.animation.start();
    }

    public AnimationTimer getAnimation() {
        return animation;
    }

    double getInitialX() {
        return initialX;
    }

    double getInitialY() {
        return initialY;
    }
}
