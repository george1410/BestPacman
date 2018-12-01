package pacman.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import pacman.GameManager;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    Canvas canvas;
    @FXML
    Group root;

    private GameManager gameManager = GameManager.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //canvas.getGraphicsContext2D().setFill(new Color(0.1, 0.1, 0.1, 1));
        //canvas.getGraphicsContext2D().fillRect(0, 0, 1225, 600);
        gameManager.setRoot(root);
        gameManager.drawBoard();
    }
}
