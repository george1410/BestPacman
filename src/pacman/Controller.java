package pacman;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class Controller {
    public Button showSetupBtn;

    @FXML
    void startGame() {
        System.out.println("Starting Game...");
    }

    @FXML
    void showSetup() throws IOException {
        System.out.println("Setup Menu...");
        Parent p = FXMLLoader.load(getClass().getResource("setup.fxml"));
        Scene theScene = new Scene( p );
        Stage stage = (Stage) showSetupBtn.getScene().getWindow();
        stage.setScene( theScene );
    }
}
