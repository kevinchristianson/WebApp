package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void iCanHasInternat() throws IOException {
        URL url = new URL("http://thacker.mathcs.carleton.edu:5107/schools/search/carleton_college");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }

    public static void main(String[] args) {
        try {
            iCanHasInternat();
        } catch (IOException e) {
            e.printStackTrace();
        }
        launch(args);
    }
}
