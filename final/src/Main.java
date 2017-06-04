import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.AllColleges;
import models.College;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("College Metrics");
        primaryStage.setScene(new Scene(root, 850, 1375.32889044));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        //List<College> collegeList = new AllColleges().getCollegeList();
        /*AllColleges allColleges = new AllColleges();
        Map<String, Double> userMetrics = new HashMap<>();
        userMetrics.put("acceptanceRate", 100.0);
        userMetrics.put("midpointACT", 00.0);
        userMetrics.put("inStateTuition", 0.0);
        userMetrics.put("outStateTuition", 0.0);
        allColleges.rankByUserMetrics(userMetrics);
        List<College> collegeList = allColleges.getCollegeList();

        for (int i = 0; i < 20; i++) {
            College college = collegeList.get(i);
            System.out.println(college.getName());
            System.out.println("Location: " + college.getState());
            System.out.println("Designation: " + college.getDesignation());
            System.out.println("Enrollment: " + college.getEnrollment());
            System.out.println("In-state tuition: " + college.getInStateTuition());
            System.out.println("Out-of-state tuition: " + college.getOutStateTuition());
            System.out.println("Acceptance rate: " + college.getAcceptanecRate());
            System.out.println("Midpoint ACT: " + college.getMidpointACT());
            System.out.println();
        }*/
        launch(args);
    }
}
