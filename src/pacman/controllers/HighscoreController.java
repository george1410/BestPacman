package pacman.controllers;

import javafx.fxml.Initializable;
import pacman.GameManager;

import java.net.URL;
import java.util.ResourceBundle;

public class HighscoreController implements Initializable {
    private GameManager gameManager = GameManager.getInstance();
    private int newScoreIndex = gameManager.getNewScoreIndex();
    private int[] highScores = gameManager.getHighScores();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < highScores.length; i++) {
            if (newScoreIndex == i) {
                System.out.println(i+1 + ". " + highScores[i] + "*** NEW ***");
            } else {
                System.out.println(i+1 + ". " + highScores[i]);
            }
        }
    }
}
