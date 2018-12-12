package pacman.models;

import javafx.scene.text.Text;

import java.io.*;

/**
 * Model for the scoreboard displayed below the maze.
 */
public class ScoreManager {

    private Text scoreText;
    private Text livesText;
    private int score;
    private int lives;
    private int[] highScores;
    private int newScoreIndex;

    /**
     * Constructor for the ScoreManager, setting default values.
     */
    public ScoreManager() {
        this.lives = 3;
        this.score = 0;
        this.highScores = readHighScores();
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

    /**
     * Reads the high scores from csv file into memory.
     *
     * @return int array of high scores.
     */
    private int[] readHighScores() {
        int[] highScores = new int[10];
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/pacman/resources/scores.csv"));
            String line = br.readLine();
            String[] splitArr = line.split(",");
            for (int i = 0; i < splitArr.length; i++) {
                highScores[i] = Integer.parseInt(splitArr[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScores;
    }

    public void updateHighScores() throws IOException {
        newScoreIndex = -1;
        if (score > highScores[9]) {
            newScoreIndex = 9;
            highScores[9] = score;
            for (int i = 9; i > 0; i--) {
                if (highScores[i] > highScores[i-1]) {
                    int temp = highScores[i];
                    highScores[i] = highScores[i-1];
                    highScores[i-1] = temp;
                    newScoreIndex = i-1;
                }
            }
            // write the new high score array to disk.
            PrintWriter pw = new PrintWriter(new FileWriter("src/pacman/resources/scores.csv"));
            for (int highScore : highScores) {
                pw.print(highScore + ",");
            }
            pw.close();
        }
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

    public int[] getHighScores() {
        return highScores;
    }

    public int getNewScoreIndex() {
        return newScoreIndex;
    }
}
