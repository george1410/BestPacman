package pacman.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pacman.GameManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SetupController implements Initializable {
    private GameManager gameManager = GameManager.getInstance();
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
                // Orange
                gameManager.setBarColor(new Color(1, 0.74, 0.26, 1));
                break;
            case 1:
                // Blue
                gameManager.setBarColor(new Color(0.29, 0.74, 1, 1));
                break;
            case 2:
                // Purple
                gameManager.setBarColor(new Color(0.29, 0.08, 0.41, 1));
                break;
            case 3:
                // Yellow
                gameManager.setBarColor(new Color(0.8, 1, 0, 1));
                break;
            case 4:
                // Green
                gameManager.setBarColor(new Color(0.06, 1, 0.58, 1));
                break;
            case 5:
                // Pink
                gameManager.setBarColor(new Color(0.77, 0.22, 0.9, 1));
                break;
            case 6:
                // Red
                gameManager.setBarColor(new Color(0.87, 0, 0.28, 1));
                break;
            case 7:
                // Turquoise
                gameManager.setBarColor(new Color(0, 0.76, 0.63, 1));
                break;
            default:
                // Default to orange
                gameManager.setBarColor(new Color(1, 0.74, 1.26, 1));
                break;
        }
    }

    @FXML
    void backgroundDropdownChange() {
        int id = backgroundDropdown.getSelectionModel().getSelectedIndex() + 1;

        gameManager.setBackgroundColor(id);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obstacleDropdown.getSelectionModel().selectFirst();
        backgroundDropdown.getSelectionModel().selectFirst();
    }
}
