package pacman;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pacman.maze.BarObstacle;

class Score {

    Text score;
    Text lives;

    Score(Group root) {
        this.score = new Text(BarObstacle.THICKNESS * 4, BarObstacle.THICKNESS * 28, "Score: 0");
        this.lives = new Text(BarObstacle.THICKNESS * 20, BarObstacle.THICKNESS * 28,"Lives: 3");
        score.setFill(Color.MAGENTA);
        score.setFont(Font.font("Arial", 30));

        lives.setFill(Color.MAROON);
        lives.setFont(Font.font("Arial", 30));

        root.getChildren().add(score);
        root.getChildren().add(lives);
    }
}
