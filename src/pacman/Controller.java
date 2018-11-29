package pacman;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class Controller {
    @FXML
    public Button showSetupBtn;
    @FXML
    public ChoiceBox colorChoice;


    @FXML
    void startGame() {
        System.out.println("Starting Game...");
        Stage stage = (Stage) showSetupBtn.getScene().getWindow();

        Group root = new Group();
        Scene theScene = new Scene(root);
        Canvas canvas = new Canvas( 1225, 600 );
        root.getChildren().add( canvas );
        GameManager gameManager = new GameManager(root);

        gameManager.drawBoard();

        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.getPacman().move(event));
        theScene.addEventHandler(KeyEvent.KEY_RELEASED, event -> gameManager.getPacman().stop(event));
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.restartGame(event));

        stage.setScene(theScene);
        stage.show();
    }

    @FXML
    void showSetup() throws IOException {
        System.out.println("Setup Menu...");
        Parent p = FXMLLoader.load(getClass().getResource("setup.fxml"));
        Scene theScene = new Scene( p );
        Stage stage = (Stage) showSetupBtn.getScene().getWindow();
        stage.setScene( theScene );
        stage.show();

    }
}
