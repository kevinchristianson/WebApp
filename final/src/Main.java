import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.AllColleges;
import models.College;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        List<College> collegeList = new AllColleges().getCollegeList();
        for (College college : collegeList) {
            System.out.println(college.getName());
            System.out.println("Location: " + college.getState());
            System.out.println("Designation: " + college.getDesignation());
            System.out.println("Enrollment: " + college.getEnrollment());
            System.out.println("In-state tuition: " + college.getInStateTuition());
            System.out.println("Out-of-state tuition: " + college.getOutStateTuition());
            System.out.println("Acceptance rate: " + college.getAcceptanecRate());
            System.out.println("Midpoint ACT: " + college.getMidpointACT());
            System.out.println();
        }
//        launch(args);
    }
}
