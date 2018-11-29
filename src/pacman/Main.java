package pacman;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage theStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        theStage.setTitle( "Pacman" );

        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}