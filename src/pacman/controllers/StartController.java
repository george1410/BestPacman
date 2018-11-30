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
    void startGame() {
        Stage stage = (Stage) showSetupBtn.getScene().getWindow();

        Group root = new Group();
        Scene theScene = new Scene(root);
        Canvas canvas = new Canvas( 1225, 600 );
        root.getChildren().add( canvas );
        canvas.getGraphicsContext2D().setFill(new Color(0.1, 0.1, 0.1, 1));
        canvas.getGraphicsContext2D().fillRect(0, 0, 1225, 600);

        gameManager.setRoot(root);
        gameManager.drawBoard();

        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.getPacman().move(event));
        theScene.addEventHandler(KeyEvent.KEY_RELEASED, event -> gameManager.getPacman().stop(event));
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.restartGame(event));

        stage.setScene(theScene);
        stage.show();
    }

    @FXML
    void showSetup() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/setup.fxml"));
        Scene theScene = new Scene(root);
        Stage stage = (Stage) showSetupBtn.getScene().getWindow();
        stage.setScene(theScene);
    }
}
