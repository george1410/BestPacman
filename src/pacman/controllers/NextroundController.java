package pacman.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import pacman.GameManager;

import java.net.URL;
import java.util.ResourceBundle;

public class NextroundController implements Initializable {
    @FXML
    Text currentScore;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentScore.setText("CURRENT SCORE: " + GameManager.getInstance().getScore());
    }
}
