package pacman.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pacman.GameManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Setup screen.
 */
public class SetupController implements Initializable {
    private GameManager gameManager = GameManager.getInstance();
    @FXML
    Button backToStartBtn;
    @FXML
    ChoiceBox obstacleDropdown;
    @FXML
    ChoiceBox backgroundDropdown;
    @FXML
    Button loadMapButton;
    @FXML
    Text mapName;
    @FXML
    Button resetMapButton;

    /**
     * Switches to the Start view.
     */
    @FXML
    void backToStart() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/start.fxml"));
        Scene theScene = new Scene(root);
        Stage stage = (Stage) backToStartBtn.getScene().getWindow();
        stage.setScene(theScene);
    }

    /**
     * Sets the obstacle color to the currently selected color in the dropdown.
     */
    @FXML
    void obstacleDropdownChange() {
        int id = obstacleDropdown.getSelectionModel().getSelectedIndex();

        gameManager.setObstacleColor(id);

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

    /**
     * Sets the background color to the currently selected color in the dropdown.
     */
    @FXML
    void backgroundDropdownChange() {
        int id = backgroundDropdown.getSelectionModel().getSelectedIndex() + 1;

        gameManager.setBackgroundColor(id);
    }

    /**
     * Called when the loadMapButton is clicked and sets the selected file as the current maze file.
     */
    @FXML
    void loadCustomMap() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Map File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".map files", "*.map"));
        File file = fileChooser.showOpenDialog(loadMapButton.getScene().getWindow());
        if (file != null) {
            gameManager.getMaze().setMazeFile(file);
            mapName.setText("Current Map: " + gameManager.getMaze().getMazeFileName());
            gameManager.getMaze().setCustomMapLoaded(true);
            resetMapButton.setVisible(gameManager.getMaze().isCustomMapLoaded());
        }
    }

    /**
     * Called when the resetMapButton is clicked, and sets the default built-in map file as the current maze file.
     */
    @FXML
    void resetMap() {
        gameManager.getMaze().setMazeFile(new File("src/pacman/resources/maze2.map"));
        gameManager.getMaze().setCustomMapLoaded(false);
        mapName.setText("Current Map: " + gameManager.getMaze().getMazeFileName());
        resetMapButton.setVisible(gameManager.getMaze().isCustomMapLoaded());
    }

    /**
     * Set default properties of the components in the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obstacleDropdown.getSelectionModel().select(gameManager.getObstacleColor());
        backgroundDropdown.getSelectionModel().select(gameManager.getBackgroundColor() - 1);
        mapName.setText("Current Map: " + gameManager.getMaze().getMazeFileName());
        resetMapButton.setVisible(gameManager.getMaze().isCustomMapLoaded());
    }
}
