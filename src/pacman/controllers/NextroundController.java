package pacman.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import pacman.GameManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Next Round screen.
 */
public class NextroundController implements Initializable {
    @FXML
    Text currentScore;

    /**
     * Set default properties of the components in the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentScore.setText("CURRENT SCORE: " + GameManager.getInstance().getScoreManager().getScore());
    }
}
