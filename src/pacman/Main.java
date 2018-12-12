package pacman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Responsible for starting the JavaFX Application, displaying the start screen.
 */
public class Main extends Application {

    /**
     * Runs after the JavaFX Application is launched and populates the stage with the start screen scene.
     *
     * @param theStage the stage object to be populated with the scene.
     * @throws Exception
     */
    @Override
    public void start(Stage theStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/start.fxml"));
        theStage.setTitle( "Pacman" );

        Scene theScene = new Scene(root);
        theStage.setScene(theScene);
        theStage.setResizable(false);
        theStage.show();
    }


    /**
     * Entry point for the application, simply launches a JavaFX application
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}