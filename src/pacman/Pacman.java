package pacman;



import javafx.scene.image.Image;
import javafx.scene.paint.Color;
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
        double pacmanCenterY = getCenterY();
        double pacmanCenterX = getCenterX();
        double pacmanLeftEdge = pacmanCenterX - getRadius();
        double pacmanRightEdge = pacmanCenterX + getRadius();
        double pacmanTopEdge = pacmanCenterY - getRadius();
        double pacmanBottomEdge = pacmanCenterY + getRadius();
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
     * @param pacman
     * @param axis
     */
    void checkCookieCoalition(Pacman pacman, String axis, Set<Cookie> cookieSet) {
        double pacmanCenterY = pacman.getCenterY();
        double pacmanCenterX = pacman.getCenterX();
        double pacmanLeftEdge = pacmanCenterX - pacman.getRadius();
        double pacmanRightEdge = pacmanCenterX + pacman.getRadius();
        double pacmanTopEdge = pacmanCenterY - pacman.getRadius();
        double pacmanBottomEdge = pacmanCenterY + pacman.getRadius();
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
}
