package pacman;



import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Set;

public class Pacman extends Circle {

    private GameManager gameManager;
    AnimationTimer leftPacmanAnimation;
    AnimationTimer rightPacmanAnimation;
    AnimationTimer upPacmanAnimation;
    AnimationTimer downPacmanAnimation;
    private Maze maze;

    public Pacman(double x, double y, GameManager gameManager, Maze maze) {
        this.gameManager = gameManager;
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(25);
        this.maze = maze;
        Image img = new Image("pacman/pacman.png");
        this.setFill(new ImagePattern(img));
        this.leftPacmanAnimation = this.createAnimation("left");
        this.rightPacmanAnimation = this.createAnimation("right");
        this.upPacmanAnimation = this.createAnimation("up");
        this.downPacmanAnimation = this.createAnimation("down");
    }

    /**
     * Checks if pacman is touching a ghost
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
     * Checks if the Pacman touches cookies.
     *
     * @param axis
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
                    gameManager.collectCookie(cookie);
                }
                // pacman goes left
                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanLeftEdge >= cookieLeftEdge && pacmanLeftEdge <= cookieRightEdge)) {
                    gameManager.collectCookie(cookie);
                }
            } else {
                // pacman goes up
                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanBottomEdge >= cookieTopEdge && pacmanBottomEdge <= cookieBottomEdge)) {
                    gameManager.collectCookie(cookie);
                }
                // pacman goes down
                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanTopEdge <= cookieBottomEdge && pacmanTopEdge >= cookieTopEdge)) {
                    gameManager.collectCookie(cookie);
                }
            }
        }
    }

    private void checkDoorway() {
        double pacmanLeftEdge = getCenterX() - getRadius();
        double pacmanRightEdge = getCenterX() + getRadius();
        if (pacmanRightEdge < 0) {
            setCenterX(49 * BarObstacle.THICKNESS + getRadius());
        } else if (pacmanLeftEdge > 49 * BarObstacle.THICKNESS + getRadius()) {
            setCenterX(0 - getRadius());
        }
    }

    void reset() {
        setRotate(0);
        setCenterX(2.5 * BarObstacle.THICKNESS);
        setCenterY(2.5 * BarObstacle.THICKNESS);
    }

    /**
     * Creates an animation of the movement.
     *
     * @param direction
     * @return
     */
    private AnimationTimer createAnimation(String direction) {
        double step = 5;
        return new AnimationTimer() {
            public void handle(long currentNanoTime) {
                switch (direction) {
                    case "left":
                        if (!maze.isTouching(getCenterX() - getRadius(), getCenterY(), 15)) {
                            setRotate(180);
                            setCenterX(getCenterX() - step);
                            checkCookieCoalition("x", maze.cookies);
                            if (checkGhostCoalition(gameManager.getGhosts()))
                                gameManager.lifeLost();
                            checkDoorway();
                        }
                        break;
                    case "right":
                        if (!maze.isTouching(getCenterX() + getRadius(), getCenterY(), 15)) {
                            setRotate(0);
                            setCenterX(getCenterX() + step);
                            checkCookieCoalition("x", maze.cookies);
                            if (checkGhostCoalition(gameManager.getGhosts()))
                                gameManager.lifeLost();
                            checkDoorway();
                        }
                        break;
                    case "up":
                        if (!maze.isTouching(getCenterX(), getCenterY() - getRadius(), 15)) {
                            setRotate(270);
                            setCenterY(getCenterY() - step);
                            checkCookieCoalition( "y", maze.cookies);
                            if (checkGhostCoalition(gameManager.getGhosts()))
                                gameManager.lifeLost();
                            checkDoorway();
                        }
                        break;
                    case "down":
                        if (!maze.isTouching(getCenterX(), getCenterY() + getRadius(), 15)) {
                            setRotate(90);
                            setCenterY(getCenterY() + step);
                            checkCookieCoalition( "y", maze.cookies);
                            if (checkGhostCoalition(gameManager.getGhosts()))
                                gameManager.lifeLost();
                            checkDoorway();
                        }
                        break;
                }
            }
        };
    }

    /**
     * Moves the pacman
     *
     * @param event
     */
    void move(KeyEvent event) {
        for (Ghost ghost : gameManager.getGhosts()) {
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

    /**
     * Stops the pacman
     *
     * @param event
     */
    void stop(KeyEvent event) {
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
