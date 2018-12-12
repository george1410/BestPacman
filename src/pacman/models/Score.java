package pacman.models;

import javafx.scene.text.Text;

/**
 * Model for the scoreboard displayed below the maze.
 */
public class Score {

    private Text score;
    private Text lives;

    /**
     * Constructor for the scoreboard, setting default values.
     *
     * @param score Text component in the view containing the current score.
     * @param lives Text component in the view showing the current lives.
     */
    public Score(Text score, Text lives) {
        this.score = score;
        this.lives = lives;
    }

    public Text getScore() {
        return score;
    }

    public Text getLives() {
        return lives;
    }
}
