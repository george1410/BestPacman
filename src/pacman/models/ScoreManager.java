package pacman.models;

import javafx.scene.text.Text;

/**
 * Model for the scoreboard displayed below the maze.
 */
public class ScoreManager {

    private Text scoreText;
    private Text livesText;
    private int score;
    private int lives;

    /**
     * Constructor for the ScoreManager, setting default values.
     */
    public ScoreManager() {
        this.lives = 3;
        this.score = 0;
    }

    /**
     * Decreases the number of lives, and takes away the points penalty, then updates the text on screen.
     */
    public void loseLife() {
        lives--;
        score -= 10;
        livesText.setText("Lives: " + this.lives);
        scoreText.setText("Score: " + this.score);
    }

    /**
     * Resets the ScoreManager to default values, then updates the text on screen.
     */
    public void reset() {
        this.lives = 3;
        this.score = 0;
        this.livesText.setText("Lives: " + this.lives);
        this.scoreText.setText("Score: " + this.score);
    }

    /**
     * Binds the view elements, and then updates them with the correct score and lives values.
     * @param scoreText Element in the view to display the current score.
     * @param livesText Element in the view to display the current lives.
     */
    public void init(Text scoreText, Text livesText) {
        this.scoreText = scoreText;
        this.livesText = livesText;
        scoreText.setText("Score: " + score);
        livesText.setText("Lives: " + lives);
    }

    public void setScore(int score) {
        this.score = score;
        this.scoreText.setText("Score: " + this.score);
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }
}
