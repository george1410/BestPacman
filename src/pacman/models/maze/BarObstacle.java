package pacman.models.maze;



import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Model for the obstacles on the map.
 */
public class BarObstacle extends Rectangle {

    public static double THICKNESS = 25;

    /**
     * Constructor for the obstacles setting default values.
     *
     * @param x The x-position of the obstacle.
     * @param y The y-position of the obstacle.
     * @param color The color of the obstacle.
     */
    BarObstacle(double x, double y, Color color) {
        this.setX(x * (2 * THICKNESS));
        this.setY(y * (2 * THICKNESS));
        this.setHeight(THICKNESS);
        this.setWidth(THICKNESS);
        this.setFill(color);
        this.setStrokeWidth(3);
    }
}
