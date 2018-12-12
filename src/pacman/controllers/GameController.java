package pacman.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import pacman.GameManager;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Game screen.
 */
public class GameController implements Initializable {
    @FXML
    Pane root;

    private GameManager gameManager = GameManager.getInstance();

    /**
     * Set default properties of the components in the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameManager.setRoot(root);
        gameManager.drawBoard();
    }
}
