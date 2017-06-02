import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.AllColleges;
import models.College;
import javafx.scene.control.ScrollPane;

import java.io.IOException;
import java.util.List;


public class Controller {
    @FXML
    private ToggleButton private_button;
    @FXML
    private ToggleButton public_button;
    @FXML
    private ToggleButton any_designation_button;
    @FXML
    private TextField state_field;
    @FXML
    private ToggleButton under_button;
    @FXML
    private ToggleButton over_button;
    @FXML
    private ToggleButton any_enrollment_button;
    @FXML
    private TextField tuition;
    @FXML
    private TextField ACT;
    @FXML
    private TextField acceptance_rate;
    @FXML
    private Label error_text;
    @FXML
    private AnchorPane results;

    private double percentage = 0.0;


    public void onStartOverButton(ActionEvent actionEvent) {
        private_button.setSelected(false);
        public_button.setSelected(false);
        any_designation_button.setSelected(false);
        state_field.setText("");
        under_button.setSelected(false);
        over_button.setSelected(false);
        any_designation_button.setSelected(false);
        tuition.setText("00");
        ACT.setText("00");
        acceptance_rate.setText("00");
        percentage = 0.0;
        error_text.setText("");
        results.getChildren().clear();
    }

    private void getResults(double weightTuition, double weightACT, double weightAcceptanceRate)throws IOException {
        List<College> collegeList = new AllColleges().getCollegeList();
        double topAnchor = 20.0;
        for (College college : collegeList) {
            Hyperlink link = new Hyperlink(college.getName());
            link.setStyle("-fx-font-size: 110%");
         //   link.setOnAction(college.getName());
            AnchorPane.setTopAnchor(link, topAnchor);
            AnchorPane.setLeftAnchor(link, 30.0);
            results.getChildren().add(link);
            topAnchor += 20.0;

        }
    }

    public void onRankCollegesButton(ActionEvent actionEvent) {
        double tuition_value;
        double ACT_value;
        double acceptance_rate_value;
        try {
            tuition_value = Double.parseDouble(tuition.getText());
            ACT_value = Double.parseDouble(ACT.getText());
            acceptance_rate_value = Double.parseDouble(acceptance_rate.getText());
            percentage =  tuition_value + ACT_value + acceptance_rate_value;
        } catch (NumberFormatException e) {
            error_text.setText("Metric value not a number");
            return;
        }
        if (percentage < 0 || percentage > 100) {
            error_text.setText("Invalid percentage");
            return;
        }
        if (0 < percentage && percentage < 100) {
            double rate = 1 / (percentage / 100);
            tuition_value = rate * tuition_value;
            ACT_value = rate * ACT_value;
            acceptance_rate_value = rate * acceptance_rate_value;
        }
        error_text.setText("");
        try {
            getResults(tuition_value, ACT_value, acceptance_rate_value);
        }catch (IOException e){
            results.getChildren().add(new Label("Error getting information"));
        }
        return;
    }

    public void unSelectButtons(ToggleButton button1, ToggleButton button2) {
        button1.setSelected(false);
        button2.setSelected(false);
    }

    public void unSelectButtonsPrivate(ActionEvent actionEvent) {
        unSelectButtons(public_button, any_designation_button);
    }

    public void unSelectButtonsPublic(ActionEvent actionEvent) {
        unSelectButtons(private_button, any_designation_button);
    }

    public void unSelectButtonsAnyDesignation(ActionEvent actionEvent) {
        unSelectButtons(public_button, private_button);
    }

    public void unSelectButtonsUnderEnroll(ActionEvent actionEvent) {
        unSelectButtons(over_button, any_enrollment_button);
    }

    public void unSelectButtonsOverEnroll(ActionEvent actionEvent) {
        unSelectButtons(under_button, any_enrollment_button);
    }

    public void unSelectButtonsAnyEnroll(ActionEvent actionEvent) {
        unSelectButtons(over_button, under_button);
    }
}
