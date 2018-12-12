package pacman.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import pacman.GameManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the High Score screen.
 */
public class HighscoreController implements Initializable {
    private GameManager gameManager = GameManager.getInstance();
    private int newScoreIndex = gameManager.getNewScoreIndex();
    private int[] highScores = gameManager.getHighScores();
    @FXML
    GridPane scoreGrid;
    @FXML
    Text yourScore;

    /**
     * Set default properties of the components in the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        yourScore.setText("YOUR SCORE: " + gameManager.getScore());
        for (int i = 0; i < highScores.length; i++) {
            Text text = new Text(i+1 + ". " + highScores[i]);
            if (i == newScoreIndex) {
                text.getStyleClass().add("new-score");
            }
            text.getStyleClass().add("high-score");

            scoreGrid.add(text, i / 5, i % 5);
        }
    }
}
