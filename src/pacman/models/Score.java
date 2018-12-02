package pacman.models;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pacman.GameManager;
import pacman.models.maze.BarObstacle;

public class Score {

    private Text score;
    private Text lives;

    public Score(Pane root) {
        this.score = new Text(BarObstacle.THICKNESS * 4, BarObstacle.THICKNESS * 28, "Score: " + GameManager.getInstance().getScore());
        this.lives = new Text(BarObstacle.THICKNESS * 20, BarObstacle.THICKNESS * 28,"Lives: " + GameManager.getInstance().getLives());
        score.setFill(Color.MAGENTA);
        score.setFont(Font.font("Arial", 30));

        lives.setFill(Color.MAROON);
        lives.setFont(Font.font("Arial", 30));

        root.getChildren().add(score);
        root.getChildren().add(lives);
    }

    public Text getScore() {
        return score;
    }

    public Text getLives() {
        return lives;
    }
}
