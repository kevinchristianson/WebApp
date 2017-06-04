import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.AllColleges;
import models.College;
import javafx.scene.control.Hyperlink;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Controller {
    @FXML
    private ToggleButton privateButton;
    @FXML
    private ToggleButton publicButton;
    @FXML
    private ToggleButton profitButton;
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
    private TextField tuitionWeight;
    @FXML
    private TextField actWeight;
    @FXML
    private TextField acceptanceRateWeight;
    @FXML
    private Label metricErrorText;
    @FXML
    private AnchorPane results;
    @FXML
    private TextField tuitionTarget;
    @FXML
    private TextField actTarget;
    @FXML
    private TextField acceptanceRateTarget;
    @FXML
    private Label valueErrorText;


    public void onStartOverButton() {
        privateButton.setSelected(false);
        publicButton.setSelected(false);
        anyDesignationButton.setSelected(false);
        stateField.setText("");
        underButton.setSelected(false);
        overButton.setSelected(false);
        anyDesignationButton.setSelected(false);
        tuitionWeight.setText("");
        actWeight.setText("");
        acceptanceRateWeight.setText("");
        metricErrorText.setText("");
        valueErrorText.setText("");
        tuitionTarget.setText("");
        actTarget.setText("");
        acceptanceRateTarget.setText("");
        results.getChildren().clear();
    }

    public void onRankCollegesButton() {
        results.getChildren().clear();
        metricErrorText.setText("");
        valueErrorText.setText("");
        double tuitionW;
        double actW;
        double acceptanceRateW;
        double tuitionT;
        double actT;
        double acceptanceRateT;
        try {
            tuitionW = getTuitionWeight();
            actW = getACTWeight();
            acceptanceRateW = getAcceptanceRateWeight();
            tuitionT = getTuitionTarget();
            actT = getACTTarget();
            acceptanceRateT = getAcceptanceRateTarget();
        } catch (NumberFormatException e) {
            metricErrorText.setText("Input value not a positive number");
            return;
        }
        metricErrorText.setText("");
        String designationDecision = "";
        if (publicButton.isSelected()) {
            designationDecision = "Public";
        } else if (privateButton.isSelected()) {
            designationDecision = "Private";
        } else if (profitButton.isSelected()) {
            designationDecision = "For-profit";
        }
        String stateDecision = stateField.getText();
        String sizeDecision = "";
        if (overButton.isSelected()) {
            sizeDecision = "Over";
        } else if (underButton.isSelected()) {
            sizeDecision = "Under";
        }
        try {
            getResults(designationDecision, stateDecision, sizeDecision, tuitionW, actW, acceptanceRateW,
                    tuitionT, actT, acceptanceRateT);
        } catch (IOException e) {
            results.getChildren().add(new Label("Error getting information"));
        }
    }

    private void getResults(String designationDecision, String stateDecision, String sizeDecision, double tuitionW,
                            double actW, double acceptanceRateW, double tuitionT, double actT, double acceptanceRateT)
                            throws IOException {
        AllColleges allColleges = new AllColleges();
        Map<String, Double> userMetrics = new HashMap<>();
        userMetrics.put("acceptanceRateW", acceptanceRateW);
        userMetrics.put("actW", actW);
        userMetrics.put("acceptanceRateT", acceptanceRateT);
        userMetrics.put("actT", actT);
        if (!stateDecision.equals("")) {
            userMetrics.put("inStateTuitionW", tuitionW);
            userMetrics.put("outStateTuitionW", 0.0);
            userMetrics.put("inStateTuitionT", tuitionT);
            userMetrics.put("outStateTuitionT", 0.0);
        } else {
            userMetrics.put("inStateTuitionW", 0.0);
            userMetrics.put("outStateTuitionW", tuitionW);
            userMetrics.put("inStateTuitionT", 0.0);
            userMetrics.put("outStateTuitionT", tuitionT);
        }
        allColleges.rankByUserMetrics(userMetrics);
        displayResults(allColleges, designationDecision, stateDecision, sizeDecision);
    }

    public void displayResults(AllColleges allColleges, String designationDecision, String stateDecision,
                               String sizeDecision) {
        double topAnchor = 20.0;
        List<College> collegeList = allColleges.getCollegeList();
        int rank = 1;
        for (College college : collegeList) {
            if (college.getDesignation().contains(designationDecision)
                    && college.getState().toLowerCase().contains(stateDecision.toLowerCase())
                    && (sizeDecision.equals("") || ((sizeDecision.equals("Over") && college.getEnrollment() >= 5000))
                    || ((sizeDecision.equals("Under") && college.getEnrollment() < 5000)))) {
                Hyperlink name = new Hyperlink(rank + ". " + college.getName());
                name.setStyle("-fx-font-size: 150%");
                Label state = new Label("State: " + college.getState());
                state.setStyle("-fx-font-size: 120%");
                Label enrollment = new Label("Enrollment: " + college.getEnrollment());
                enrollment.setStyle("-fx-font-size: 120%");
                Label designation = new Label("Designation: " + college.getDesignation());
                designation.setStyle("-fx-font-size: 120%");
                Label tuition = new Label();
                tuition.setStyle("-fx-font-size: 120%");
                if (stateDecision.equals("")) {
                    tuition.setText("Out-of-state tuition: " + college.getOutStateTuition());
                } else {
                    tuition.setText("In-state tuition: " + college.getInStateTuition());
                }
                Label acceptance = new Label("Acceptance Rate: " + college.getAcceptanecRate());
                acceptance.setStyle("-fx-font-size: 120%");
                Label act = new Label("Midpoint ACT: " + college.getMidpointACT());
                act.setStyle("-fx-font-size: 120%");
                AnchorPane.setTopAnchor(name, topAnchor);
                topAnchor += 28.0;
                AnchorPane.setLeftAnchor(name, 30.0);
                results.getChildren().add(name);
                AnchorPane.setTopAnchor(state, topAnchor);
                topAnchor += 15;
                AnchorPane.setLeftAnchor(state, 60.0);
                results.getChildren().add(state);
                AnchorPane.setTopAnchor(enrollment, topAnchor);
                topAnchor += 15;
                AnchorPane.setLeftAnchor(enrollment, 60.0);
                results.getChildren().add(enrollment);
                AnchorPane.setTopAnchor(designation, topAnchor);
                topAnchor += 15;
                AnchorPane.setLeftAnchor(designation, 60.0);
                results.getChildren().add(designation);
                AnchorPane.setTopAnchor(tuition, topAnchor);
                topAnchor += 15;
                AnchorPane.setLeftAnchor(tuition, 60.0);
                results.getChildren().add(tuition);
                AnchorPane.setTopAnchor(acceptance, topAnchor);
                topAnchor += 15;
                AnchorPane.setLeftAnchor(acceptance, 60.0);
                results.getChildren().add(acceptance);
                AnchorPane.setTopAnchor(act, topAnchor);
                topAnchor += 30;
                AnchorPane.setLeftAnchor(act, 60.0);
                results.getChildren().add(act);
                rank++;
            }
        }
        checkForNoResults();
    }

    public void checkForNoResults() {
        if (results.getChildren().isEmpty()){
            Label text = new Label();
            text.setText("No results found");
            text.setStyle("-fx-font-size: 150%");
            AnchorPane.setTopAnchor(text, 75.0);
            AnchorPane.setLeftAnchor(text, 225.0);
            results.getChildren().add(text);
        }
    }

    public double getACTWeight() throws  NumberFormatException {
        if (!actWeight.getText().equals("")) {
            return Double.parseDouble(actWeight.getText());
        }
        return 0;
    }

    public double getAcceptanceRateWeight() throws  NumberFormatException {
        if (!acceptanceRateWeight.getText().equals("")) {
            return Double.parseDouble(acceptanceRateWeight.getText());
        }
        return 0;
    }

    public double getTuitionWeight() throws  NumberFormatException {
        if (!tuitionWeight.getText().equals("")) {
            return Double.parseDouble(tuitionWeight.getText());
        }
        return 0;
    }

    public double getACTTarget() throws  NumberFormatException {
        if (!actTarget.getText().equals("")) {
            return Double.parseDouble(actTarget.getText());
        }
        return 0;
    }

    public double getAcceptanceRateTarget() throws  NumberFormatException {
        if (!acceptanceRateTarget.getText().equals("")) {
            return Double.parseDouble(acceptanceRateTarget.getText());
        }
        return 0;
    }

    public double getTuitionTarget() throws  NumberFormatException {
        if (!tuitionTarget.getText().equals("")) {
            return Double.parseDouble(tuitionTarget.getText());
        }
        return 0;
    }

    public void unselectButtons(ToggleButton button1, ToggleButton button2, ToggleButton button3) {
        button1.setSelected(false);
        button2.setSelected(false);
        button3.setSelected(false);
    }

    public void unselectButtons(ToggleButton button1, ToggleButton button2) {
        button1.setSelected(false);
        button2.setSelected(false);
    }

    public void unselectButtonsPrivate() {
        unselectButtons(publicButton, profitButton, anyDesignationButton);
    }

    public void unselectButtonsPublic() {
        unselectButtons(privateButton, profitButton, anyDesignationButton);
    }

    public void unselectButtonsProfit(){
        unselectButtons(publicButton, privateButton, anyDesignationButton);
    }

    public void unselectButtonsAnyDesignation() {
        unselectButtons(publicButton, privateButton, profitButton);
    }

    public void unselectButtonsUnderEnroll() {
        unselectButtons(overButton, anyEnrollmentButton);
    }
    public void unselectButtonsOverEnroll() {
        unselectButtons(underButton, anyEnrollmentButton);
    }
    public void unSelectButtonsAnyEnroll() {
        unselectButtons(overButton, underButton);
    }
}