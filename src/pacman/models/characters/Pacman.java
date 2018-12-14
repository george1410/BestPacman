package pacman.models.characters;


import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import pacman.GameManager;
import pacman.models.maze.BarObstacle;
import pacman.models.maze.Cookie;
import pacman.models.maze.Maze;

import java.io.IOException;
import java.util.Set;

/**
 * Model for the Pacman character.
 */
public class Pacman extends Circle implements Moveable {

    private GameManager gameManager;
    private AnimationTimer leftPacmanAnimation;
    private AnimationTimer rightPacmanAnimation;
    private AnimationTimer upPacmanAnimation;
    private AnimationTimer downPacmanAnimation;
    private Maze maze;
    private double initialX;
    private double initialY;

    /**
     * Constructor initialises default values for Pacman.
     *
     * @param x The x-position of Pacman.
     * @param y the y-position of Pacman.
     * @param maze The maze instance that Pacman is being used in.
     * @param gameManager Reference to the GameManager singleton.
     */
    public Pacman(double x, double y, Maze maze, GameManager gameManager) {
        this.maze = maze;
        this.gameManager = gameManager;
        this.setCenterX(x);
        this.setCenterY(y);
        this.initialX = x;
        this.initialY = y;
        this.setRadius(25);
        Image img = new Image("pacman/resources/pacman.png");
        this.setFill(new ImagePattern(img));
        this.leftPacmanAnimation = this.createAnimation("left");
        this.rightPacmanAnimation = this.createAnimation("right");
        this.upPacmanAnimation = this.createAnimation("up");
        this.downPacmanAnimation = this.createAnimation("down");
    }

    /**
     * Checks if Pacman is touching a Ghost.
     *
     * @param ghosts The set of Ghosts to be checked against.
     * @return Whether or not Pacman is currently touching a Ghost.
     */
    boolean checkGhostCoalition(Set<Ghost> ghosts) {
        double pacmanLeftEdge = getCenterX() - getRadius();
        double pacmanRightEdge = getCenterX() + getRadius();
        double pacmanTopEdge = getCenterY() - getRadius();
        double pacmanBottomEdge = getCenterY() + getRadius();
        for (Ghost ghost : ghosts) {
            double ghostLeftEdge = ghost.getX();
            double ghostRightEdge = ghost.getX() + ghost.getWidth();
            double ghostTopEdge = ghost.getY();
            double ghostBottomEdge = ghost.getY() + ghost.getHeight();
            if ((pacmanLeftEdge <= ghostRightEdge && pacmanLeftEdge >= ghostLeftEdge) || (pacmanRightEdge >= ghostLeftEdge && pacmanRightEdge <= ghostRightEdge)) {
                if ((pacmanTopEdge <= ghostBottomEdge && pacmanTopEdge >= ghostTopEdge) || (pacmanBottomEdge >= ghostTopEdge && pacmanBottomEdge <= ghostBottomEdge)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if Pacman is touching a cookie, and collects the cookie if it is.
     *
     * @param axis Describes the current direction of travel for Pacman.
     * @param cookieSet The set of cookies to be checked against.
     */
    private void checkCookieCoalition(String axis, Set<Cookie> cookieSet) {
        double pacmanLeftEdge = getCenterX() - getRadius();
        double pacmanRightEdge = getCenterX() + getRadius();
        double pacmanTopEdge = getCenterY() - getRadius();
        double pacmanBottomEdge = getCenterY() + getRadius();
        for (Cookie cookie : cookieSet) {
            double cookieCenterX = cookie.getCenterX();
            double cookieCenterY = cookie.getCenterY();
            double cookieLeftEdge = cookieCenterX - cookie.getRadius();
            double cookieRightEdge = cookieCenterX + cookie.getRadius();
            double cookieTopEdge = cookieCenterY - cookie.getRadius();
            double cookieBottomEdge = cookieCenterY + cookie.getRadius();
            if (axis.equals("x")) {
                // pacman goes right
                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanRightEdge >= cookieLeftEdge && pacmanRightEdge <= cookieRightEdge)) {
                    collectCookie(cookie);
                }
                // pacman goes left
                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanLeftEdge >= cookieLeftEdge && pacmanLeftEdge <= cookieRightEdge)) {
                    collectCookie(cookie);
                }
            } else {
                // pacman goes up
                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanBottomEdge >= cookieTopEdge && pacmanBottomEdge <= cookieBottomEdge)) {
                    collectCookie(cookie);
                }
                // pacman goes down
                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanTopEdge <= cookieBottomEdge && pacmanTopEdge >= cookieTopEdge)) {
                    collectCookie(cookie);
                }
            }
        }
    }

    /**
     * Checks whether the Ghost is travelling through a doorway, and moves it to the other doorway if necessary.
     */
    public void checkDoorway() {
        double pacmanLeftEdge = getCenterX() - getRadius();
        double pacmanRightEdge = getCenterX() + getRadius();
        if (pacmanRightEdge < 0) {
            setCenterX((maze.getWidth() * 2 - 0.5) * BarObstacle.THICKNESS);
        } else if (pacmanLeftEdge > (maze.getWidth() * 2 - 1) * BarObstacle.THICKNESS) {
            setCenterX(-0.5 * BarObstacle.THICKNESS);
        }
    }

    /**
     * Stops Pacman moving, and returns to the original starting position.
     */
    public void reset() {
        setRotate(0);
        setCenterX(initialX);
        setCenterY(initialY);
        leftPacmanAnimation.stop();
        rightPacmanAnimation.stop();
        upPacmanAnimation.stop();
        downPacmanAnimation.stop();
    }

    /**
     * Creates an animation of the movement.
     *
     * @param direction The direction to create a movement animation for.
     * @return The movement animation.
     */
    public AnimationTimer createAnimation(String direction) {
        double step = 5;
        return new AnimationTimer() {
            public void handle(long currentNanoTime) {
                switch (direction) {
                    case "left":
                        if (!maze.hasObstacle(getCenterX() - getRadius() - 15, getCenterX() - getRadius(), getCenterY() - getRadius(), getCenterY() + getRadius())) {
                            setRotate(180);
                            setCenterX(getCenterX() - step);
                            checkCookieCoalition("x", maze.getCookies());
                            if (checkGhostCoalition(maze.getGhosts()))
                                touchedGhost();
                            checkDoorway();
                        }
                        break;
                    case "right":
                        if (!maze.hasObstacle(getCenterX() + getRadius(), getCenterX() + getRadius() + 15, getCenterY() - getRadius() , getCenterY() + getRadius())) {
                            setRotate(0);
                            setCenterX(getCenterX() + step);
                            checkCookieCoalition("x", maze.getCookies());
                            if (checkGhostCoalition(maze.getGhosts()))
                                touchedGhost();
                            checkDoorway();
                        }
                        break;
                    case "up":
                        if (!maze.hasObstacle(getCenterX() - getRadius(), getCenterX() + getRadius(), getCenterY() - getRadius() - 15, getCenterY() - getRadius())) {
                            setRotate(270);
                            setCenterY(getCenterY() - step);
                            checkCookieCoalition( "y", maze.getCookies());
                            if (checkGhostCoalition(maze.getGhosts()))
                                touchedGhost();
                            checkDoorway();
                        }
                        break;
                    case "down":
                        if (!maze.hasObstacle(getCenterX() - getRadius(), getCenterX() + getRadius(), getCenterY() + getRadius(), getCenterY() + getRadius() + 15)) {
                            setRotate(90);
                            setCenterY(getCenterY() + step);
                            checkCookieCoalition( "y", maze.getCookies());
                            if (checkGhostCoalition(maze.getGhosts()))
                                touchedGhost();
                            checkDoorway();
                        }
                        break;
                }
            }
        };
    }

    /**
     * Moves the pacman by starting the relevant animation.
     *
     * @param event The keyevent which the desired direction of movement can be extracted from.
     */
    public void move(KeyEvent event) {
        if (!gameManager.isPaused()) {
            for (Ghost ghost : maze.getGhosts()) {
                ghost.run();
            }
            switch (event.getCode()) {
                case RIGHT:
                    rightPacmanAnimation.start();
                    break;
                case LEFT:
                    leftPacmanAnimation.start();
                    break;
                case UP:
                    upPacmanAnimation.start();
                    break;
                case DOWN:
                    downPacmanAnimation.start();
                    break;
            }
        }
    }

    /**
     * Stops pacman moving in the specified direction.
     *
     * @param event The keyevent which the direction of movement to stop can be extracted from.
     */
    public void stop(KeyEvent event) {
        if (!gameManager.isPaused()) {
            switch (event.getCode()) {
                case RIGHT:
                    rightPacmanAnimation.stop();
                    break;
                case LEFT:
                    leftPacmanAnimation.stop();
                    break;
                case UP:
                    upPacmanAnimation.stop();
                    break;
                case DOWN:
                    downPacmanAnimation.stop();
                    break;
            }
        }
    }

    /**
     * Called if Pacman and Ghosts collide to reduce number of lives, reset Pacman to start position, and play sound.
     */
    void touchedGhost() {
        for (AnimationTimer animation : this.getAllAnimations()) {
            animation.stop();
        }

        for (Ghost ghost : maze.getGhosts()) {
            ghost.getAnimation().stop();
        }

        maze.getPacman().reset();
        gameManager.getScoreManager().loseLife();

        if (gameManager.getScoreManager().getLives() == 0) {
            gameManager.playSound("src/pacman/resources/lose_sound.wav");
            try {
                gameManager.setGameLost(true);
                gameManager.endGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (Ghost ghost : maze.getGhosts()) {
            ghost.setX(ghost.getInitialX());
            ghost.setY(ghost.getInitialY());
        }
    }

    /**
     * Called when Pacman collides with a cookie, handles updating score.
     *
     * @param cookie The cookie object that Pacman collided with.
     */
    private void collectCookie(Cookie cookie) {
        if (cookie.isVisible()) {
            gameManager.getScoreManager().setScore(gameManager.getScoreManager().getScore() + cookie.getValue());
            gameManager.increaseCookiesEaten();
        }
        cookie.hide();
        if (gameManager.getCookiesEaten() == maze.getCookies().size() && !gameManager.isGameEnded()) {
            gameManager.playSound("src/pacman/resources/win_sound.wav");
            for (AnimationTimer animation : maze.getPacman().getAllAnimations()) {
                animation.stop();
            }

            for (Ghost ghost : maze.getGhosts()) {
                ghost.getAnimation().stop();
            }
            try {
                gameManager.endGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Allows access to all of the Pacman animations.
     * @return Array of the Animations for each direction.
     */
    public AnimationTimer[] getAllAnimations() {
        return new AnimationTimer[]{leftPacmanAnimation, rightPacmanAnimation, upPacmanAnimation, downPacmanAnimation};
    }
}
