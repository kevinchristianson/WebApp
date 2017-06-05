import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Kevin Christianson and Isaac Haseley
 *
 * Launches our College Ranker application
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("final.fxml"));
        primaryStage.getProperties().put("hostServices", this.getHostServices());
        primaryStage.setTitle("College Ranker");
        primaryStage.setScene(new Scene(root, 850, 650));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
