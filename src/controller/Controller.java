package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class Controller {

    @FXML
    private Label testo;
    public Button updateButton;

    @FXML
    private void updateButton(ActionEvent event) {
        System.out.println("You clicked me!");
        testo.setText("Hello world!");
    }

}
