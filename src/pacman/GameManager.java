package pacman;



import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameManager {

    private Pacman pacman;
    private Group root;
    private Set<Cookie> cookieSet;
    private Set<Ghost> ghosts;
    private AnimationTimer leftPacmanAnimation;
    private AnimationTimer rightPacmanAnimation;
    private AnimationTimer upPacmanAnimation;
    private AnimationTimer downPacmanAnimation;
    private Maze maze;
    private int lifes;
    private int score;
    private Score scoreBoard;
    private boolean gameEnded;
    private int cookiesEaten;

    /**
     * Constructor
     */
    GameManager(Group root) {
        this.root = root;
        this.maze = new Maze();
        this.pacman = new Pacman(2.5 * BarObstacle.THICKNESS, 2.5 * BarObstacle.THICKNESS, this);
        this.cookieSet = new HashSet<>();
        this.ghosts = new HashSet<>();
        this.leftPacmanAnimation = this.createAnimation("left");
        this.rightPacmanAnimation = this.createAnimation("right");
        this.upPacmanAnimation = this.createAnimation("up");
        this.downPacmanAnimation = this.createAnimation("down");
        this.lifes = 3;
        this.score = 0;
        this.cookiesEaten = 0;
    }

    /**
     * Set one life less
     */
    void lifeLost() {
        this.leftPacmanAnimation.stop();
        this.rightPacmanAnimation.stop();
        this.upPacmanAnimation.stop();
        this.downPacmanAnimation.stop();
        for (Ghost ghost : ghosts) {
            ghost.getAnimation().stop();
        }
        this.pacman.reset();
        lifes--;
        score -= 10;
        this.scoreBoard.lifes.setText("Lifes: " + this.lifes);
        this.scoreBoard.score.setText("Score: " + this.score);
        if (lifes == 0) {
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
        root.getChildren().remove(this.scoreBoard.score);
        root.getChildren().remove(this.scoreBoard.lifes);
        root.getChildren().add(endGame);
    }

    /**
     * Restart the game
     *
     * @param event
     */
    void restartGame(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE && gameEnded) {
            root.getChildren().clear();
            this.cookieSet.clear();
            this.ghosts.clear();
            this.drawBoard();
            this.pacman.reset();
            this.lifes = 3;
            this.score = 0;
            this.cookiesEaten = 0;
            gameEnded = false;
        }
    }

    /**
     * Draws the board of the game with the cookies and the Pacman
     */
    void drawBoard() {
        this.maze.CreateMaze(root);

        // defines cookie positions
        Integer skip[][] = {
                {5, 17},
                {1, 2, 3, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 19, 20, 21},
                {1, 21},
                {1, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 21},
                {1, 7, 8, 9, 10, 11, 12, 13, 14, 15, 21},
                {3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19},
                {1, 7, 8, 9, 10, 11, 12, 13, 14, 15, 21},
                {1, 3, 4, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 18, 19, 21},
                {1, 21},
                {1, 2, 3, 5, 7, 8, 9, 10, 11, 12, 13, 14, 15, 17, 19, 20, 21},
                {5, 17}
        };

        // iterate through each line, and column of the grid and place cookies.
        double offset = 2.5;
        for (int line = 0; line < 11; line++) {
            for (int i = 0; i < 23; i++) {
                if (!Arrays.asList(skip[line]).contains(i)) {
                    Cookie cookie = new Cookie(((2 * i) + 2.5) * BarObstacle.THICKNESS, offset * BarObstacle.THICKNESS);
                    this.cookieSet.add(cookie);
                    root.getChildren().add(cookie);
                }
            }
            offset += 2;
        }

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

    /**
     * Moves the pacman
     *
     * @param event
     */
    void movePacman(KeyEvent event) {
        for (Ghost ghost : this.ghosts) {
            ghost.run();
        }
        switch (event.getCode()) {
            case RIGHT:
                this.rightPacmanAnimation.start();
                break;
            case LEFT:
                this.leftPacmanAnimation.start();
                break;
            case UP:
                this.upPacmanAnimation.start();
                break;
            case DOWN:
                this.downPacmanAnimation.start();
                break;
        }
    }

    /**
     * Stops the pacman
     *
     * @param event
     */
     void stopPacman(KeyEvent event) {
        switch (event.getCode()) {
            case RIGHT:
                this.rightPacmanAnimation.stop();
                break;
            case LEFT:
                this.leftPacmanAnimation.stop();
                break;
            case UP:
                this.upPacmanAnimation.stop();
                break;
            case DOWN:
                this.downPacmanAnimation.stop();
                break;
        }
    }

    /**
     * Creates an animation of the movement.
     *
     * @param direction
     * @return
     */
    private AnimationTimer createAnimation(String direction) {
        double step = 5;
        return new AnimationTimer() {
            public void handle(long currentNanoTime) {
                switch (direction) {
                    case "left":
                        if (!maze.isTouching(pacman.getCenterX() - pacman.getRadius(), pacman.getCenterY(), 15)) {
                            pacman.setRotate(180);
                            pacman.setCenterX(pacman.getCenterX() - step);
                            pacman.checkCookieCoalition("x", cookieSet);
                            if (pacman.checkGhostCoalition(ghosts))
                                lifeLost();
                            pacman.checkDoorway();
                        }
                        break;
                    case "right":
                        if (!maze.isTouching(pacman.getCenterX() + pacman.getRadius(), pacman.getCenterY(), 15)) {
                            pacman.setRotate(0);
                            pacman.setCenterX(pacman.getCenterX() + step);
                            pacman.checkCookieCoalition("x", cookieSet);
                            if (pacman.checkGhostCoalition(ghosts))
                                lifeLost();
                            pacman.checkDoorway();
                        }
                        break;
                    case "up":
                        if (!maze.isTouching(pacman.getCenterX(), pacman.getCenterY() - pacman.getRadius(), 15)) {
                            pacman.setRotate(270);
                            pacman.setCenterY(pacman.getCenterY() - step);
                            pacman.checkCookieCoalition( "y", cookieSet);
                            if (pacman.checkGhostCoalition(ghosts))
                                lifeLost();
                            pacman.checkDoorway();
                        }
                        break;
                    case "down":
                        if (!maze.isTouching(pacman.getCenterX(), pacman.getCenterY() + pacman.getRadius(), 15)) {
                            pacman.setRotate(90);
                            pacman.setCenterY(pacman.getCenterY() + step);
                            pacman.checkCookieCoalition( "y", cookieSet);
                            if (pacman.checkGhostCoalition(ghosts))
                                lifeLost();
                            pacman.checkDoorway();
                        }
                        break;
                }
            }
        };
    }

    void collectCookie(Cookie cookie) {
        if (cookie.isVisible()) {
            this.score += cookie.getValue();
            this.cookiesEaten++;
        }
        cookie.hide();
        scoreBoard.score.setText("Score: " + score);
        if (cookiesEaten == cookieSet.size()) {
            endGame();
        }
    }

    Pacman getPacman() {
        return pacman;
    }

    Set<Ghost> getGhosts() {
        return ghosts;
    }
}

