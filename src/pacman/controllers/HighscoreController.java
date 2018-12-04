package pacman.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import pacman.GameManager;

import java.net.URL;
import java.util.ResourceBundle;

public class HighscoreController implements Initializable {
    private GameManager gameManager = GameManager.getInstance();
    private int newScoreIndex = gameManager.getNewScoreIndex();
    private int[] highScores = gameManager.getHighScores();
    @FXML
    GridPane scoreGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < highScores.length; i++) {
            Text text = new Text(i+1 + ". " + highScores[i]);
            if (i == newScoreIndex) {
                text.getStyleClass().add("new-score");
            }
            text.getStyleClass().add("high-score");

            GridPane.setHalignment(text, HPos.CENTER);
            scoreGrid.add(text, i / 5, i % 5);
        }
    }
}
