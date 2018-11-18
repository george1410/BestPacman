package pacman;



import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Set;

public class Pacman extends Circle {

    public Pacman(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(25);
        Image img = new Image("pacman/pacman.png");
        this.setFill(new ImagePattern(img));
    }

    /**
     * Checks if pacman is touching a ghost
     */
    public boolean checkGhostCoalition(Set<Ghost> ghosts) {
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
}
