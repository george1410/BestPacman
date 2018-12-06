package pacman.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pacman.GameManager;

import java.io.IOException;

public class StartController {
    @FXML
    public Button showSetupBtn;

    private GameManager gameManager = GameManager.getInstance();

    @FXML
    void startGame() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/game.fxml"));
        Scene theScene = new Scene(root);

        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.getMaze().getPacman().move(event));
        theScene.addEventHandler(KeyEvent.KEY_RELEASED, event -> gameManager.getMaze().getPacman().stop(event));
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            try {
                gameManager.restartGame(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Stage stage = (Stage) showSetupBtn.getScene().getWindow();
        stage.setScene(theScene);
        gameManager.setStage(stage);
    }

    @FXML
    void showSetup() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/setup.fxml"));
        Scene theScene = new Scene(root);
        Stage stage = (Stage) showSetupBtn.getScene().getWindow();
        stage.setScene(theScene);
    }
}
