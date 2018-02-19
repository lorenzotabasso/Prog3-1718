package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label label;
    public Button updateButton;

    @FXML
    private void updateButton(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

}
