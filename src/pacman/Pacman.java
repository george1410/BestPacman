package pacman;



import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Set;

public class Pacman extends Circle {

    private GameManager gameManager;

    public Pacman(double x, double y, GameManager gameManager) {
        this.gameManager = gameManager;
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(25);
        Image img = new Image("pacman/pacman.png");
        this.setFill(new ImagePattern(img));
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
    void checkCookieCoalition(String axis, Set<Cookie> cookieSet) {
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

    void checkDoorway() {
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
}
