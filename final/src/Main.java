import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;
import JSONParser;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void iCanHasInternat() throws IOException {
//        URL url = new URL("http://thacker.mathcs.carleton.edu:5107/schools/search/carleton_college");
//        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//        String inputLine;
//        Map<String, String> myMap = new HashMap<>();
//        String[] pairs = inputLine.split(",");
//        for (int i = 0; i < pairs.length; i++) {
//            String pair = pairs[i]
//            String[] keyValue = pair.split(":");
//            myMap.put(keyValue[0], Integer.valueOf(keyValue[1]));
//        }
////        while ((inputLine = in.readLine()) != null)
////            System.out.println(inputLine);
//        in.close();
        JSONParser parser = new JSONParser();

    }

    public static void main(String[] args) {
//        try {
//            iCanHasInternat();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        launch(args);
    }
}
