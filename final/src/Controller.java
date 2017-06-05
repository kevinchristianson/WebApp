import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import models.AllColleges;
import models.College;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kevin Christianson and Isaac Haseley
 *
 * Controller for our College Ranker application
 */
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
    private Label errorText;
    @FXML
    private AnchorPane results;
    @FXML
    private TextField tuitionTarget;
    @FXML
    private TextField actTarget;
    @FXML
    private TextField acceptanceRateTarget;

    /**
     * Clears user input and button selections
     */
    public void onStartOverButton() {
        privateButton.setSelected(false);
        publicButton.setSelected(false);
        profitButton.setSelected(false);
        anyDesignationButton.setSelected(false);
        stateField.setText("");
        underButton.setSelected(false);
        overButton.setSelected(false);
        anyEnrollmentButton.setSelected(false);
        tuitionWeight.setText("");
        actWeight.setText("");
        acceptanceRateWeight.setText("");
        errorText.setText("");
        tuitionTarget.setText("");
        actTarget.setText("");
        acceptanceRateTarget.setText("");
        results.getChildren().clear();
    }

    /**
     * Reads user input and button selections
     * Calls helpers to rank and display colleges
     */
    public void onRankCollegesButton() {
        errorText.setText("");
        results.getChildren().clear();
        // "W" for "weight," "T" for "target"
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
            if (tuitionT == College.NO_TARGET_INPUT && actT == College.NO_TARGET_INPUT
                    && acceptanceRateT == College.NO_TARGET_INPUT && tuitionW == 0 && actW == 0 && acceptanceRateW == 0) {
                errorText.setText("Alert: Sorting alphabetically");
            }
            if ((tuitionT >= 0 && tuitionT != College.NO_TARGET_INPUT && tuitionW <= 0)
                    || (actT >= 0 && actT != College.NO_TARGET_INPUT && actW <= 0)
                    || (acceptanceRateT >= 0 && acceptanceRateT != College.NO_TARGET_INPUT && acceptanceRateW <= 0)) {
                errorText.setText("Alert: Target has zero weight");
            }
        } catch (NumberFormatException e) {
            errorText.setText("Please input positive numbers");
            return;
        }
        if (tuitionW < 0 || actW < 0 || acceptanceRateW < 0 || tuitionT < 0 || actT < 0 || acceptanceRateT < 0) {
            errorText.setText("Please input positive numbers");
            return;
        }
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
            AllColleges allColleges = getRankedColleges(!stateDecision.equals(""), tuitionW, actW, acceptanceRateW,
                    tuitionT, actT, acceptanceRateT);
            filterAndDisplayResults(allColleges, designationDecision, stateDecision, sizeDecision);
        } catch (IOException e) {
            Label message = new Label("Database not properly configured");
            message.setStyle("-fx-font-size: 150%");
            AnchorPane.setLeftAnchor(message, 100.0);
            AnchorPane.setTopAnchor(message, 30.0);
            results.getChildren().add(message);
        }
    }

    /**
     * Gets colleges from API and ranks them using user input
     * @param inState - Did the user enter something in the "State" field? This determines whether we'll use in-state
     *                or out-of-state tuition when ranking
     * @param tuitionW - Weight for tuition metric
     * @param actW - Weight for midpoint ACT metric
     * @param acceptanceRateW - Weight for acceptance rate metric
     * @param tuitionT - Target value for tuition
     * @param actT - Target value for midpoint ACT
     * @param acceptanceRateT - Target value for acceptance rate
     * @return AllColleges object with a stored list of ranked Colleges
     * @throws IOException - If connection to database failed
     */
    private AllColleges getRankedColleges(boolean inState, double tuitionW, double actW, double acceptanceRateW,
                                          double tuitionT, double actT, double acceptanceRateT) throws IOException {
        Map<String, Double> userMetrics = new HashMap<>();
        userMetrics.put("acceptanceRateW", acceptanceRateW);
        userMetrics.put("actW", actW);
        userMetrics.put("acceptanceRateT", acceptanceRateT);
        userMetrics.put("actT", actT);
        userMetrics.put("tuitionT", tuitionT);
        if (inState) {
            userMetrics.put("inStateTuitionW", tuitionW);
            userMetrics.put("outStateTuitionW", 0.0);
        } else {
            userMetrics.put("inStateTuitionW", 0.0);
            userMetrics.put("outStateTuitionW", tuitionW);
        }
        AllColleges allColleges = new AllColleges();
        allColleges.rankByUserMetrics(userMetrics);
        return allColleges;
    }

    /**
     * Filters Colleges based on user input
     * Displays top 100 based on user input
     * @param allColleges - A previously-ranked AllColleges object
     * @param designationDecision - Public, Private, For-profit, or "" for Any
     * @param stateDecision - User's input in state field
     * @param sizeDecision - Over, Under, or "" for Any
     */
    public void filterAndDisplayResults(AllColleges allColleges, String designationDecision, String stateDecision,
                                        String sizeDecision) {
        double topAnchor = 20.0;
        List<College> collegeList = allColleges.getCollegeList();
        int rank = 1;
        for (College college : collegeList) {
            if (rank <= 100 && college.getDesignation().contains(designationDecision)
                    && college.getState().toLowerCase().contains(stateDecision.toLowerCase())
                    && (sizeDecision.equals("") || ((sizeDecision.equals("Over") && college.getEnrollment() >= 5000))
                    || ((sizeDecision.equals("Under") && college.getEnrollment() < 5000)))) {
                displayName(college, topAnchor, rank);
                topAnchor += 28.0;
                displayState(college, topAnchor);
                topAnchor += 15;
                displayEnrollment(college, topAnchor);
                topAnchor += 15;
                displayDesignation(college, topAnchor);
                topAnchor += 15;
                displayTuition(college, topAnchor, !stateDecision.equals(""));
                topAnchor += 15;
                displayAcceptance(college, topAnchor);
                topAnchor += 15;
                displayACT(college, topAnchor);
                topAnchor += 30;
                rank++;
            }
        }
        checkForNoResults();
    }

    /**
     * Displays a "no results" message if none are found
     */
    public void checkForNoResults() {
        if (results.getChildren().isEmpty()){
            Label text = new Label();
            text.setText("No results found");
            text.setStyle("-fx-font-size: 150%");
            AnchorPane.setTopAnchor(text, 30.0);
            AnchorPane.setLeftAnchor(text, 175.0);
            results.getChildren().add(text);
        }
    }

    // ===== RETURN USER INPUT =====

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
        return College.NO_TARGET_INPUT;
    }

    public double getAcceptanceRateTarget() throws  NumberFormatException {
        if (!acceptanceRateTarget.getText().equals("")) {
            return Double.parseDouble(acceptanceRateTarget.getText());
        }
        return College.NO_TARGET_INPUT;
    }

    public double getTuitionTarget() throws  NumberFormatException {
        if (!tuitionTarget.getText().equals("")) {
            return Double.parseDouble(tuitionTarget.getText().replace(",", ""));
        }
        return College.NO_TARGET_INPUT;
    }

    // ===== FORMAT AND DISPLAY A COLLEGE'S ATTRIBUTES =====

    public void displayName(College college, double topAnchor, int rank) {
        Label name = new Label(rank + ". " + college.getName());
        name.setStyle("-fx-font-size: 150%");
        AnchorPane.setTopAnchor(name, topAnchor);
        AnchorPane.setLeftAnchor(name, 30.0);
        results.getChildren().add(name);
    }

    public void displayState(College college, double topAnchor) {
        Label state = new Label("State: " + college.getState());
        state.setStyle("-fx-font-size: 120%");
        AnchorPane.setTopAnchor(state, topAnchor);
        AnchorPane.setLeftAnchor(state, 60.0);
        results.getChildren().add(state);
    }

    public void displayEnrollment(College college, double topAnchor) {
        Label enrollment = new Label();
        if (college.getEnrollment() == -1) {
            enrollment.setText("Enrollment: Data not available");
        } else {
            String size = Integer.toString(college.getEnrollment());
            if (size.length() == 4) {
                size = size.substring(0, 1) + "," + size.substring(1, 4);
            } else if (size.length() == 5) {
                size = size.substring(0, 2) + "," + size.substring(2, 5);
            }
            enrollment.setText("Enrollment: " + size);
        }
        enrollment.setStyle("-fx-font-size: 120%");
        AnchorPane.setTopAnchor(enrollment, topAnchor);
        AnchorPane.setLeftAnchor(enrollment, 60.0);
        results.getChildren().add(enrollment);
    }

    public void displayDesignation(College college, double topAnchor) {
        Label designation = new Label("Designation: " + college.getDesignation());
        designation.setStyle("-fx-font-size: 120%");
        AnchorPane.setTopAnchor(designation, topAnchor);
        AnchorPane.setLeftAnchor(designation, 60.0);
        results.getChildren().add(designation);
    }

    public String formatTuition(int tuition) {
        String formattedTuition = Integer.toString(tuition);
        if (formattedTuition.length() == 4) {
            formattedTuition = formattedTuition.substring(0, 1) + "," + formattedTuition.substring(1, 4);
        } else if (formattedTuition.length() == 5) {
            formattedTuition = formattedTuition.substring(0, 2) + "," + formattedTuition.substring(2, 5);
        }
        return formattedTuition;
    }

    public void displayTuition(College college, double topAnchor, boolean inState) {
        Label tuition = new Label();
        if (college.getOutStateTuition() == -1 || college.getInStateTuition() == -1) {
            if (inState) {
                tuition.setText("In-state tuition: Data not available");
            } else {
                tuition.setText("Out-of-state tuition: Data not available");
            }
        } else {
            if (inState) {
                tuition.setText("In-state tuition: $" + formatTuition(college.getInStateTuition()));
            } else {
                tuition.setText("Out-of-state tuition: $" + formatTuition(college.getOutStateTuition()));
            }
        }
        tuition.setStyle("-fx-font-size: 120%");
        AnchorPane.setTopAnchor(tuition, topAnchor);
        AnchorPane.setLeftAnchor(tuition, 60.0);
        results.getChildren().add(tuition);
    }

    public void displayAcceptance(College college, double topAnchor) {
        Label acceptance = new Label();
        if (college.getAcceptanecRate() == -1) {
            acceptance.setText("Acceptance rate: Data not available");
        } else {
            acceptance.setText("Acceptance rate: " + college.getAcceptanecRate() + "%");
        }
        acceptance.setStyle("-fx-font-size: 120%");
        AnchorPane.setTopAnchor(acceptance, topAnchor);
        AnchorPane.setLeftAnchor(acceptance, 60.0);
        results.getChildren().add(acceptance);
    }

    public void displayACT(College college, double topAnchor) {
        Label act = new Label();
        if (college.getMidpointACT() == -1) {
            act.setText("Midpoint ACT: Data not available");
        } else {
            act.setText("Midpoint ACT: " + college.getMidpointACT());
        }
        act.setStyle("-fx-font-size: 120%");
        AnchorPane.setTopAnchor(act, topAnchor);
        AnchorPane.setLeftAnchor(act, 60.0);
        results.getChildren().add(act);
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

    // ===== BUTTONS =====

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