import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.AllColleges;
import models.College;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private TextField tuitionValue;
    @FXML
    private TextField actValue;
    @FXML
    private TextField acceptanceRateValue;
    @FXML
    private Label valueErrorText;


    public void onStartOverButton(ActionEvent actionEvent) {
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
        results.getChildren().clear();
    }

    /*private void getResults() throws IOException{
        List<College> collegeList = new AllColleges().getCollegeList();
        double topAnchor = 20.0;
        String designation = "";
        if (publicButton.isSelected()) {
            designation = "Public";
        } else if (privateButton.isSelected()) {
            designation = "Private";
        }
        String state = stateField.getText();
        int sizeDecision = 0;
        if (overButton.isSelected()) {
            sizeDecision = 1;
        } else if (underButton.isSelected()) {
            sizeDecision = 2;
        }
        for (College college : collegeList) {
            if (college.getDesignation().contains(designation)
                    && college.getState().toLowerCase().contains(state.toLowerCase())) {
                if (sizeDecision == 0 || (sizeDecision == 1 && college.getEnrollment() >= 5000)
                        || (sizeDecision == 2 && college.getEnrollment() < 5000)) {
                    Hyperlink link = new Hyperlink(college.getName());
                    link.setStyle("-fx-font-size: 110%");
                    //   link.setOnAction(college.getName());
                    AnchorPane.setTopAnchor(link, topAnchor);
                    AnchorPane.setLeftAnchor(link, 30.0);
                    results.getChildren().add(link);
                    topAnchor += 20.0;
                }
            }
        }
    }

    private void getResults(double weightTuition, double weightACT, double weightAcceptanceRate)throws IOException {
        List<College> collegeList = new AllColleges().getCollegeList();
        double topAnchor = 20.0;
        PriorityQueue<Double> ranks = new PriorityQueue<>();
        HashMap<Double, College> dict = new HashMap<>();
        String designation = "";
        if (publicButton.isSelected()) {
            designation = "Public";
        } else if (privateButton.isSelected()) {
            designation = "Private";
        }
        String state = stateField.getText();
        int sizeDecision = 0;
        if (overButton.isSelected()) {
            sizeDecision = 1;
        } else if (underButton.isSelected()) {
            sizeDecision = 2;
        }
        for (College college : collegeList) {
            if (college.getDesignation().contains(designation)
                    && college.getState().toLowerCase().contains(state.toLowerCase())) {
                if (sizeDecision == 0 || (sizeDecision == 1 && college.getEnrollment() > 5000)
                        || (sizeDecision == 2 && college.getEnrollment() < 5000)) {
                    Double rank = 0.0;
                    if (college.getInStateTuition() != -1) {
                        rank += (((53000 - (double)college.getInStateTuition()) / 53000) * 100) * weightTuition;
                    }
                    if (college.getMidpointACT() != -1) {
                        rank += ((college.getMidpointACT() / 36) * 100) * weightACT;
                    }
                    if(college.getAcceptanecRate() != -1) {
                        rank += (100 - college.getAcceptanecRate()) * weightAcceptanceRate;
                    }
                    if (rank != 0.0) {
                        while (dict.get(rank) != null && !dict.isEmpty()) {
                            rank -= 1;
                        }
                        dict.put(rank, college);
                        ranks.add(rank);
                    }
                }
            }
        }
        while(!ranks.isEmpty() && !ranks.isEmpty()){
            College college = dict.get(ranks.peek());
            dict.remove(ranks.poll());
            Hyperlink link = new Hyperlink(college.getName());
            link.setStyle("-fx-font-size: 110%");
            //   link.setOnAction(college.getName());
            AnchorPane.setTopAnchor(link, topAnchor);
            AnchorPane.setLeftAnchor(link, 30.0);
            results.getChildren().add(link);
            topAnchor += 20.0;
        }
    }*/

    private void getResults(double weightTuition, double weightACT, double weightAcceptanceRate) throws IOException {
        double topAnchor = 20.0;
        String designation = "";
        if (publicButton.isSelected()) {
            designation = "Public";
        } else if (privateButton.isSelected()) {
            designation = "Private";
        }
        String state = stateField.getText();
        String sizeDecision = "";
        if (overButton.isSelected()) {
            sizeDecision = "Over";
        } else if (underButton.isSelected()) {
            sizeDecision = "Under";
        }

        AllColleges allColleges = new AllColleges();
        Map<String, Double> userMetrics = new HashMap<>();
        userMetrics.put("acceptanceRate", weightAcceptanceRate);
        userMetrics.put("midpointACT", weightACT);
        if (!state.equals("")) {
            userMetrics.put("inStateTuition", weightTuition);
            userMetrics.put("outStateTuition", 0.0);
        } else {
            userMetrics.put("inStateTuition", 0.0);
            userMetrics.put("outStateTuition", weightTuition);
        }
        allColleges.rankByUserMetrics(userMetrics);

        List<College> collegeList = allColleges.getCollegeList();
        int rank = 1;
        for (College college : collegeList) {
            if (college.getDesignation().contains(designation)
                    && college.getState().toLowerCase().contains(state.toLowerCase())) {
                if (sizeDecision.equals("") || (sizeDecision.equals("Over") && college.getEnrollment() > 5000)
                        || (sizeDecision.equals("Under") && college.getEnrollment() < 5000)) {
                    Hyperlink link = new Hyperlink(rank + ". " + college.getName());
                    link.setStyle("-fx-font-size: 110%");
                    //   link.setOnAction(college.getName());
                    AnchorPane.setTopAnchor(link, topAnchor);
                    AnchorPane.setLeftAnchor(link, 30.0);
                    results.getChildren().add(link);
                    topAnchor += 20.0;
                    rank++;
                }
            }
        }
        if(results.getChildren().isEmpty()){
            Label text = new Label();
            text.setText("No results found");
            text.setStyle("-fx-font-size: 150%");
            AnchorPane.setTopAnchor(text, 75.0);
            AnchorPane.setLeftAnchor(text, 225.0);
            results.getChildren().add(text);
        }
    }

    public void onRankCollegesButton(ActionEvent actionEvent) {
        results.getChildren().clear();
        double tuitionValue = 0;
        double actValue = 0;
        double acceptanceRateValue = 0;
        double percentage = 0;
        if (!tuitionWeight.getText().equals("")) {
            try {
                tuitionValue = Double.parseDouble(tuitionWeight.getText());
            } catch (NumberFormatException e) {
                metricErrorText.setText("Metric value not a number");
                return;
            }
        }
        if (!actWeight.getText().equals("")) {
            try {
                actValue = Double.parseDouble(actWeight.getText());
            } catch (NumberFormatException e) {
                metricErrorText.setText("Metric value not a number");
                return;
            }
        }
        if (!acceptanceRateWeight.getText().equals("")) {
            try {
                acceptanceRateValue = Double.parseDouble(acceptanceRateWeight.getText());
            } catch (NumberFormatException e) {
                metricErrorText.setText("Metric value not a number");
                return;
            }
        }
        percentage =  tuitionValue + actValue + acceptanceRateValue;
        if (percentage < 0) {
            metricErrorText.setText("Weights must not be negative");
            return;
        }
        metricErrorText.setText("");
        try {
            getResults(tuitionValue, actValue, acceptanceRateValue);
        } catch (IOException e) {
            results.getChildren().add(new Label("Error getting information"));
        }
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