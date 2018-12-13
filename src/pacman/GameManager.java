package pacman;


import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pacman.models.ScoreManager;
import pacman.models.characters.Ghost;
import pacman.models.maze.Cookie;
import pacman.models.maze.Maze;

import java.io.*;

/**
 * Singleton class that stores and manages the current global state of the game.
 */
public final class GameManager {

    private Maze maze;
    private ScoreManager scoreManager;
    private boolean gameEnded;
    private int cookiesEaten;
    private Stage stage;
    private int backgroundColor;
    private int obstacleColor;
    private boolean gameLost;

    private static GameManager theGameManager = new GameManager();

    /**
     * Allows access to the GameManager singleton instance.
     *
     * @return The GameManager singleton instance.
     */
    public static GameManager getInstance() {
        return theGameManager;
    }

    /**
     * Constructor initialises default values for the fields.
     */
    private GameManager() {
        this.maze = new Maze(new Color(1, 0.74, 0.26, 1));
        this.scoreManager = new ScoreManager();
        this.cookiesEaten = 0;
        this.backgroundColor = 1;
        this.obstacleColor = 0;
    }

    /**
     * Called when all coins are collected or all lives are lost to end the round/game.
     */
    public void endGame() throws IOException {
        this.gameEnded = true;

        Parent root1;

        if (gameLost) {
            scoreManager.updateHighScores();
            root1 = FXMLLoader.load(getClass().getResource("views/highscore.fxml"));
        } else {
            root1 = FXMLLoader.load(getClass().getResource("views/nextround.fxml"));
        }

        Scene theScene = new Scene(root1);
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event1 -> {
            try {
                this.restartGame(event1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.stage.setScene(theScene);
    }

    /**
     * Restart the game and reset game state, if the KeyCode of the parameter is ESCAPE.
     *
     * @param event A keypress event from which the exact key pressed is extracted.
     */
    public void restartGame(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE) {
            maze.getCookies().clear();
            maze.getObstacles().clear();
            maze.getGhosts().clear();
            maze.getPacman().reset();

            if (gameLost) {
                this.scoreManager.reset();
                this.gameLost = false;
            }

            this.cookiesEaten = 0;
            Scene theScene;
            if (gameEnded) {
                gameEnded = false;

                Parent root1 = FXMLLoader.load(getClass().getResource("views/game.fxml"));
                theScene = new Scene(root1);

                theScene.addEventHandler(KeyEvent.KEY_PRESSED, event1 -> maze.getPacman().move(event1));
                theScene.addEventHandler(KeyEvent.KEY_RELEASED, event1 -> maze.getPacman().stop(event1));
                theScene.addEventHandler(KeyEvent.KEY_PRESSED, event1 -> {
                    try {
                        this.restartGame(event1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                this.scoreManager.reset();
                Parent root1 = FXMLLoader.load(getClass().getResource("views/start.fxml"));
                theScene = new Scene(root1);
            }

            this.stage.setScene(theScene);
        }
    }

    /**
     * Responsible for ensuring that a maze and score area are added to the scene.
     */
    public void drawBoard(Pane root) {
        root.getStyleClass().removeAll();
        root.getStyleClass().add("ui-background" + backgroundColor);

        this.maze.CreateMaze(root);
    }

    /**
     * Plays a sound file.
     *
     * @param filepath Path to the sound file to be played.
     */
    public void playSound(String filepath) {
        Media sound = new Media(new File(filepath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getObstacleColor() {
        return obstacleColor;
    }

    public void setObstacleColor(int obstacleColor) {
        this.obstacleColor = obstacleColor;
    }

    public void setBarColor(Color color) {
        this.maze.setBarColor(color);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Maze getMaze() {
        return maze;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }

    public void increaseCookiesEaten() {
        this.cookiesEaten++;
    }

    public int getCookiesEaten() {
        return cookiesEaten;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }
}