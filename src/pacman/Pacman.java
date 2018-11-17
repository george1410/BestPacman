package pacman;



import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Pacman extends Circle {

    public Pacman(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(25);
        Image img = new Image("pacman/pacman.png");
        this.setFill(new ImagePattern(img));
    }
}
