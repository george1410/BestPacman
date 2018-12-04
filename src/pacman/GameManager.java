package pacman;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pacman.models.Score;
import pacman.models.characters.Ghost;
import pacman.models.characters.Pacman;
import pacman.models.maze.BarObstacle;
import pacman.models.maze.Cookie;
import pacman.models.maze.Maze;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class GameManager {

    private Pacman pacman;
    private Pane root;
    private Set<Ghost> ghosts;
    private Maze maze;
    private int lives;
    private int score;
    private Score scoreBoard;
    private boolean gameEnded;
    private int cookiesEaten;
    private Stage stage;
    private int backgroundColor;
    private int obstacleColor;
    private boolean gameLost;
    public int[] highScores;

    private static GameManager theGameManager = new GameManager();

    public static GameManager getInstance() {
        return theGameManager;
    }

    /**
     * Constructor
     */
    private GameManager() {
        this.maze = new Maze(new Color(1, 0.74, 0.26, 1));
        this.pacman = new Pacman(2.5 * BarObstacle.THICKNESS, 2.5 * BarObstacle.THICKNESS, this, maze);
        this.ghosts = new HashSet<>();
        this.lives = 3;
        this.score = 0;
        this.cookiesEaten = 0;
        this.backgroundColor = 1;
        this.obstacleColor = 0;
        this.highScores = readHighScores();
    }

    private int[] readHighScores() {
        int[] highScores = new int[10];
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/pacman/resources/scores.csv"));
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                highScores[count] = Integer.parseInt(line);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highScores;
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

    public void setRoot(Pane root) {
        this.root = root;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Set one life less
     */
    public void lifeLost() {
        pacman.getLeftPacmanAnimation().stop();
        pacman.getRightPacmanAnimation().stop();
        pacman.getUpPacmanAnimation().stop();
        pacman.getDownPacmanAnimation().stop();
        for (Ghost ghost : ghosts) {
            ghost.getAnimation().stop();
        }
        this.pacman.reset();
        lives--;
        score -= 10;
        this.scoreBoard.getLives().setText("Lives: " + this.lives);
        this.scoreBoard.getScore().setText("Score: " + this.score);
        if (lives == 0) {
            try {
                this.gameLost = true;
                this.endGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Ends the game
     */
    private void endGame() throws IOException {
        this.gameEnded = true;

        Parent root1;

        if (gameLost) {
            int newScoreIndex = -1;
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
            }
            for (int i = 0; i < highScores.length; i++) {
                if (newScoreIndex == i) {
                    System.out.println(i+1 + ". " + highScores[i] + "*** NEW ***");
                } else {
                    System.out.println(i+1 + ". " + highScores[i]);
                }
            }
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
     * Restart the game
     *
     * @param event
     */
    public void restartGame(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ESCAPE && gameEnded) {
            maze.getCookies().clear();
            this.ghosts.clear();
            this.pacman.reset();

            if (gameLost) {
                this.lives = 3;
                this.score = 0;
                this.gameLost = false;
            }

            this.cookiesEaten = 0;
            gameEnded = false;

            Parent root1 = FXMLLoader.load(getClass().getResource("views/game.fxml"));
            Scene theScene = new Scene(root1);

            theScene.addEventHandler(KeyEvent.KEY_PRESSED, event1 -> this.getPacman().move(event1));
            theScene.addEventHandler(KeyEvent.KEY_RELEASED, event1 -> this.getPacman().stop(event1));

            this.stage.setScene(theScene);
        }
    }

    /**
     * Draws the board of the game with the cookies and the Pacman
     */
    public void drawBoard() {
        root.getStyleClass().removeAll();
        root.getStyleClass().add("ui-background" + backgroundColor);

        this.maze.CreateMaze(root);

        root.getChildren().add(this.pacman);
        this.generateGhosts();
        root.getChildren().addAll(this.ghosts);
        this.scoreBoard = new Score(root);
    }

    /**
     * Generates the ghosts for the pacman!
     */
    private void generateGhosts() {
        this.ghosts.add(new Ghost(18.5 * BarObstacle.THICKNESS, 12.5 * BarObstacle.THICKNESS, 1, maze, this));
        this.ghosts.add(new Ghost(22.5 * BarObstacle.THICKNESS, 12.5 * BarObstacle.THICKNESS, 2, maze, this));
        this.ghosts.add(new Ghost(28.5 * BarObstacle.THICKNESS, 12.5 * BarObstacle.THICKNESS, 3, maze, this));
        this.ghosts.add(new Ghost(28.5 * BarObstacle.THICKNESS, 9.5 * BarObstacle.THICKNESS, 4, maze, this));
    }

    public void collectCookie(Cookie cookie) {
        if (cookie.isVisible()) {
            this.score += cookie.getValue();
            this.cookiesEaten++;
        }
        cookie.hide();
        scoreBoard.getScore().setText("Score: " + score);
        if (cookiesEaten == maze.getCookies().size() && !gameEnded) {
            try {
                endGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Pacman getPacman() {
        return pacman;
    }

    public Set<Ghost> getGhosts() {
        return ghosts;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }
}

