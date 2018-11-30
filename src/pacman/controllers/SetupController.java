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
    ChoiceBox obstacleDropdown;
    @FXML
    ChoiceBox backgroundDropdown;

    @FXML
    void backToStart() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/start.fxml"));
        Scene theScene = new Scene(root);
        Stage stage = (Stage) backToStartBtn.getScene().getWindow();
        stage.setScene(theScene);
    }

    @FXML
    void obstacleDropdownChange() {
        int id = obstacleDropdown.getSelectionModel().getSelectedIndex();

        switch (id) {
            case 0:
                // Blue
                gameManager.setBarColor(new Color(0, 0, 1, 1));
                break;
            case 1:
                // Red
                gameManager.setBarColor(new Color(1, 0, 0, 1));
                break;
            case 2:
                // Green
                gameManager.setBarColor(new Color(0, 1, 0, 1));
                break;
        }
    }

    @FXML
    void backgroundDropdownChange() {
        //TODO
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obstacleDropdown.getSelectionModel().selectFirst();
        backgroundDropdown.getSelectionModel().selectFirst();
    }
}
