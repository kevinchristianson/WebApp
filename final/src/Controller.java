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
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;


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


    public void onStartOverButton(ActionEvent actionEvent) {
        private_button.setSelected(false);
        public_button.setSelected(false);
        any_designation_button.setSelected(false);
        state_field.setText("");
        under_button.setSelected(false);
        over_button.setSelected(false);
        any_designation_button.setSelected(false);
        tuition.setText("");
        ACT.setText("");
        acceptance_rate.setText("");
        error_text.setText("");
        results.getChildren().clear();
    }

    private void getResults() throws IOException{
        List<College> collegeList = new AllColleges().getCollegeList();
        double topAnchor = 20.0;
        String designation = "";
        if(public_button.isSelected()){
            designation = "Public";
        }else if(private_button.isSelected()){
            designation = "Private";
        }
        String state = state_field.getText();
        int sizeDecision = 0;
        if(over_button.isSelected()){
            sizeDecision = 1;
        }else if (under_button.isSelected()){
            sizeDecision = 2;
        }
        for (College college : collegeList) {
            if(college.getDesignation().contains(designation) && college.getState().contains(state)){
                if(sizeDecision == 0 || (sizeDecision == 1 && college.getEnrollment() > 5000) || (sizeDecision == 2 && college.getEnrollment() < 5000)){
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
        if(public_button.isSelected()){
            designation = "Public";
        }else if(private_button.isSelected()){
            designation = "Private";
        }
        String state = state_field.getText();
        int sizeDecision = 0;
        if(over_button.isSelected()){
            sizeDecision = 1;
        }else if (under_button.isSelected()){
            sizeDecision = 2;
        }
        for (College college : collegeList) {
            if(college.getDesignation().contains(designation) && college.getState().contains(state)){
                if(sizeDecision == 0 || (sizeDecision == 1 && college.getEnrollment() > 5000) || (sizeDecision == 2 && college.getEnrollment() < 5000)){
                    Double rank = 0.0;
                    if(college.getInStateTuition() != -1) {
                        rank += (((5300 - college.getInStateTuition()) / 53000) * 100) * weightTuition;
                    }
                    if(college.getMidpointACT() != -1) {
                        rank += ((college.getMidpointACT() / 36) * 100) * weightACT;
                    }
                    if(college.getAcceptanecRate() != -1) {
                        rank += (100 - college.getAcceptanecRate()) * weightAcceptanceRate;
                    }
                    if(rank != 0.0) {
                        while(dict.get(rank) != null && !dict.isEmpty()){
                            rank -= 1;
                        }
                        dict.put(rank, college);
                        ranks.add(rank);
                    }
                }
            }
        }
        System.out.println(ranks.size());
        System.out.println(dict.get(ranks.peek()).getName());
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
    }

    public void onRankCollegesButton(ActionEvent actionEvent) {
        results.getChildren().clear();
        double tuition_value = 0;
        double ACT_value = 0;
        double acceptance_rate_value = 0;
        double percentage = 0;
        if(tuition.getText().equals("") && ACT.getText().equals("") && acceptance_rate.getText().equals("")){
            error_text.setText("");
            try {
                getResults();
                return;
            }catch (IOException e){
                results.getChildren().add(new Label("Error getting information"));
            }
        }else if(tuition.getText().equals("") || ACT.getText().equals("") || acceptance_rate.getText().equals("")){
            if(tuition.getText().equals("")){
                tuition_value = 0;
            }else{
                tuition_value = Double.parseDouble(tuition.getText());
            }
            if(ACT.getText().equals("")){
                ACT_value = 0;
            }else{
                ACT_value = Double.parseDouble(ACT.getText());
            }if(acceptance_rate.getText().equals("")){
                acceptance_rate_value = 0;
            }else{
                acceptance_rate_value = Double.parseDouble(acceptance_rate.getText());
            }
        }else{
            try {
                tuition_value = Double.parseDouble(tuition.getText());
                ACT_value = Double.parseDouble(ACT.getText());
                acceptance_rate_value = Double.parseDouble(acceptance_rate.getText());
            } catch (NumberFormatException e) {
                error_text.setText("Metric value not a number");
                return;
            }
        }
        percentage =  tuition_value + ACT_value + acceptance_rate_value;
        if (percentage < 0 || percentage > 100) {
            error_text.setText("Invalid percentage");
            return;
        }
        if(percentage == 0){
            tuition_value = 1/3;
            ACT_value = 1/3;
            acceptance_rate_value = 1/3;
        }
        else if (0 < percentage && percentage < 100) {
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
