package pacman.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import pacman.GameManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SetupController implements Initializable {
    GameManager gameManager = GameManager.getInstance();
    @FXML
    Button backToStartBtn;

    @FXML
    void backToStart() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/start.fxml"));
        Scene theScene = new Scene(root);
        Stage stage = (Stage) backToStartBtn.getScene().getWindow();
        stage.setScene(theScene);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
