package pacman.models.maze;



import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Model for the cookies on the map.
 */
public class Cookie extends Circle {

    private int value;

    /**
     * Constructor to set default cookie values.
     *
     * @param x The x-position of the cookie.
     * @param y The y-position of the cookie.
     */
    Cookie(double x, double y) {
        this.value = 5;
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(12.5);
        this.setFill(new Color(0.8, 1, 0, 1));
    }

    /**
     * Hides the cookie from the map.
     */
    public void hide() {
        this.setVisible(false);
    }

    public int getValue() {
        return value;
    }
}