package pacman;



import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import pacman.models.Score;
import pacman.models.characters.Ghost;
import pacman.models.characters.Pacman;
import pacman.models.maze.BarObstacle;
import pacman.models.maze.Cookie;
import pacman.models.maze.Maze;

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

    private static GameManager theGameManager = new GameManager();

    public static GameManager getInstance() {
        return theGameManager;
    }

    /**
     * Constructor
     */
    private GameManager() {
        this.maze = new Maze(Color.CADETBLUE);
        this.pacman = new Pacman(2.5 * BarObstacle.THICKNESS, 2.5 * BarObstacle.THICKNESS, this, maze);
        this.ghosts = new HashSet<>();
        this.lives = 3;
        this.score = 0;
        this.cookiesEaten = 0;
    }

    public void setBarColor(Color color) {
        this.maze.setBarColor(color);
    }

    public void setRoot(Pane root) {
        this.root = root;
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
            this.endGame();
        }
    }

    /**
     * Ends the game
     */
    private void endGame() {
        this.gameEnded = true;
        root.getChildren().remove(pacman);
        for (Ghost ghost : ghosts) {
            root.getChildren().remove(ghost);
        }
        javafx.scene.text.Text endGame = new javafx.scene.text.Text("Game Over, press ESC to restart");
        endGame.setX(BarObstacle.THICKNESS * 3);
        endGame.setY(BarObstacle.THICKNESS * 28);
        endGame.setFont(Font.font("Arial", 40));
        endGame.setFill(Color.ROYALBLUE);
        root.getChildren().remove(this.scoreBoard.getScore());
        root.getChildren().remove(this.scoreBoard.getLives());
        root.getChildren().add(endGame);
    }

    /**
     * Restart the game
     *
     * @param event
     */
    public void restartGame(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE && gameEnded) {
            root.getChildren().clear();
            maze.getCookies().clear();
            this.ghosts.clear();
            this.drawBoard();
            this.pacman.reset();
            this.lives = 3;
            this.score = 0;
            this.cookiesEaten = 0;
            gameEnded = false;
        }
    }

    /**
     * Draws the board of the game with the cookies and the Pacman
     */
    public void drawBoard() {
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
        if (cookiesEaten == maze.getCookies().size()) {
            endGame();
        }
    }

    public Pacman getPacman() {
        return pacman;
    }

    public Set<Ghost> getGhosts() {
        return ghosts;
    }
}

