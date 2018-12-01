package pacman.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import pacman.GameManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController {
    @FXML
    public Button showSetupBtn;

    private GameManager gameManager = GameManager.getInstance();

    @FXML
    void startGame() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/game.fxml"));
        Scene theScene = new Scene(root);

        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.getPacman().move(event));
        theScene.addEventHandler(KeyEvent.KEY_RELEASED, event -> gameManager.getPacman().stop(event));
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.restartGame(event));

        Stage stage = (Stage) showSetupBtn.getScene().getWindow();
        stage.setScene(theScene);
    }

    @FXML
    void showSetup() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/setup.fxml"));
        Scene theScene = new Scene(root);
        Stage stage = (Stage) showSetupBtn.getScene().getWindow();
        stage.setScene(theScene);
    }
}
