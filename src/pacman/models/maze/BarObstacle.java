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
    public BarObstacle(double x, double y) {
        this.setX(x * THICKNESS);
        this.setY(y * THICKNESS);
        this.setHeight(THICKNESS);
        this.setWidth(THICKNESS);
        this.setFill(Color.CADETBLUE);
        this.setStrokeWidth(3);
    }
}
