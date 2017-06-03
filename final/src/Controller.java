import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.AllColleges;
import models.College;

import java.io.IOException;
import java.util.List;


public class Controller {
    @FXML
    private ToggleButton privateButton;
    @FXML
    private ToggleButton publicButton;
    @FXML
    private ToggleButton anyDesignationButton;
    @FXML
    private TextField stateField;
    @FXML
    private ToggleButton underButton;
    @FXML
    private ToggleButton overButton;
    @FXML
    private ToggleButton anyEnrollmentButton;
    @FXML
    private TextField tuition;
    @FXML
    private TextField act;
    @FXML
    private TextField acceptanceRate;
    @FXML
    private Label errorText;
    @FXML
    private AnchorPane results;

    private double percentage = 0.0;


    public void onStartOverButton(ActionEvent actionEvent) {
        privateButton.setSelected(false);
        publicButton.setSelected(false);
        anyDesignationButton.setSelected(false);
        stateField.setText("");
        underButton.setSelected(false);
        overButton.setSelected(false);
        anyDesignationButton.setSelected(false);
        tuition.setText("00");
        act.setText("00");
        acceptanceRate.setText("00");
        percentage = 0.0;
        errorText.setText("");
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
            ACT_value = Double.parseDouble(act.getText());
            acceptance_rate_value = Double.parseDouble(acceptanceRate.getText());
            percentage =  tuition_value + ACT_value + acceptance_rate_value;
        } catch (NumberFormatException e) {
            errorText.setText("Metric value not a number");
            return;
        }
        if (percentage < 0 || percentage > 100) {
            errorText.setText("Invalid percentage");
            return;
        }
        if (0 < percentage && percentage < 100) {
            double rate = 1 / (percentage / 100);
            tuition_value = rate * tuition_value;
            ACT_value = rate * ACT_value;
            acceptance_rate_value = rate * acceptance_rate_value;
        }
        errorText.setText("");
        try {
            getResults(tuition_value, ACT_value, acceptance_rate_value);
        }catch (IOException e){
            results.getChildren().add(new Label("Error getting information"));
        }
        return;
    }

    public void unselectButtons(ToggleButton button1, ToggleButton button2) {
        button1.setSelected(false);
        button2.setSelected(false);
    }

    public void unselectButtonsPrivate(ActionEvent actionEvent) {
        unselectButtons(publicButton, anyDesignationButton);
    }

    public void unselectButtonsPublic(ActionEvent actionEvent) {
        unselectButtons(privateButton, anyDesignationButton);
    }

    public void unselectButtonsAnyDesignation(ActionEvent actionEvent) {
        unselectButtons(publicButton, privateButton);
    }

    public void unselectButtonsUnderEnroll(ActionEvent actionEvent) {
        unselectButtons(overButton, anyEnrollmentButton);
    }

    public void unselectButtonsOverEnroll(ActionEvent actionEvent) {
        unselectButtons(underButton, anyEnrollmentButton);
    }

    public void unSelectButtonsAnyEnroll(ActionEvent actionEvent) {
        unselectButtons(overButton, underButton);
    }
}