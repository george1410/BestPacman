package pacman.models.maze;



import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BarObstacle extends Rectangle {

    public static double THICKNESS = 25;
    /**
     *
     * @param x
     * @param y
     */
    public BarObstacle(double x, double y, Color color) {
        this.setX(x * THICKNESS);
        this.setY(y * THICKNESS);
        this.setHeight(THICKNESS);
        this.setWidth(THICKNESS);
        this.setFill(color);
        this.setStrokeWidth(3);
    }
}
