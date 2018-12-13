package pacman.models;

import javafx.scene.text.Text;
import pacman.GameManager;

import java.io.*;
import java.util.LinkedList;

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
    public ScoreManager(String fileHash) {
        this.lives = 3;
        this.score = 0;
        this.highScores = readHighScores(fileHash);
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
    private int[] readHighScores(String fileHash) {
        int[] highScores = new int[10];
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/pacman/resources/scores.csv"));

            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                String[] splitArr = line.split(",");
                if (splitArr[0].equals(fileHash)) {
                    for (int i = 0; i < 10; i++) {
                        highScores[i] = Integer.parseInt(splitArr[i+1]);
                    }
                    found = true;
                }
            }
            if (!found) {
                for (int i = 0; i < 10; i++) {
                    highScores[i] = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScores;
    }

    public void updateHighScores(String fileHash) throws IOException {
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

            BufferedReader br = new BufferedReader(new FileReader("src/pacman/resources/scores.csv"));
            LinkedList<String> lines = new LinkedList<>();
            String line;
            while ((line = br.readLine()) != null && !line.equals("")) {
                lines.add(line);
            }

            boolean written = false;
            PrintWriter pw = new PrintWriter(new FileWriter("src/pacman/resources/scores.csv"));
            for (String theLine : lines) {
                String[] spitLine = theLine.split(",");
                String firstEl = spitLine[0];
                if (firstEl.equals(fileHash)) {
                    // update and write.
                    pw.print(fileHash + ",");
                    for (int highScore : highScores) {
                        pw.print(highScore + ",");
                    }
                    pw.println();
                    written = true;
                } else {
                    // write.
                    pw.println(theLine);
                }
            }
            if (!written) {
                pw.print(fileHash + ",");
                for (int highScore : highScores) {
                    pw.print(highScore + ",");
                }
                pw.println();
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

    public void setHighScores(String fileHash) {
        this.highScores = readHighScores(fileHash);
    }
}
