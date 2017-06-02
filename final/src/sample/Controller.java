package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;


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
    }

    public void onRankCollegesButton(ActionEvent actionEvent) {
        if(percentage < 0 || (0 < percentage && percentage < 100)){
            // print error message to screen
            return;
        }
        return;
    }

    public void unSelectButtons(ToggleButton button1, ToggleButton button2){
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
