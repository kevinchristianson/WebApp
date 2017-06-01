import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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
        String apiOutput = in.readLine();

        JsonParser myParser = new JsonParser();
        JsonElement myElement = myParser.parse(apiOutput);
        JsonArray myArray = myElement.getAsJsonArray();
        JsonObject myObject = myArray.get(0).getAsJsonObject();
        System.out.println(myObject.get("name"));

    }

    public static void main(String[] args) {
        try {
            iCanHasInternat();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        launch(args);
    }
}
