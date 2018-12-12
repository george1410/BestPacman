package pacman.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import pacman.GameManager;
import pacman.models.ScoreManager;
import pacman.models.maze.BarObstacle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Game screen.
 */
public class GameController implements Initializable {
    @FXML
    Pane root;
    @FXML
    Text scoreText;
    @FXML
    Text livesText;


    private GameManager gameManager = GameManager.getInstance();

    /**
     * Set default properties of the components in the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameManager.setRoot(root);
        gameManager.drawBoard();
        scoreText.setLayoutY(gameManager.getMaze().getHeight() * BarObstacle.THICKNESS + BarObstacle.THICKNESS);
        livesText.setLayoutY(gameManager.getMaze().getHeight() * BarObstacle.THICKNESS + BarObstacle.THICKNESS);
        scoreText.setFill(gameManager.getMaze().getBarColor());
        livesText.setFill(gameManager.getMaze().getBarColor());
        scoreText.setText("Score: " + gameManager.getScore());
        livesText.setText("Lives: " + gameManager.getLives());

        gameManager.setScoreManager(new ScoreManager(scoreText, livesText));
    }
}
