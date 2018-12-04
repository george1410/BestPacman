package pacman.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
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
        Text text = new Text("1. 1250");
        text.getStyleClass().add("setup-label");
        scoreGrid.add(text, 0, 0);
    }
}
