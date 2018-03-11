package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Account;
import model.Email;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class WriteViewController implements Initializable, Observer{

    public WriteViewController() {

    }

    @FXML
    public VBox root;
    public GridPane table;
    public TextField to;
    public TextField from;
    public TextField subject;
    public TextArea text;
    public ToolBar toolbar;
    public Button send;
    public Button saveAsDraft;
    public Button delete;

    // BUTTONS ---------------------------------------------------------------------------------------------------------

    /**
     * On click on Update button do something
     */
    @FXML
    public void sendButton() {
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Account reciver = new Account(to.getText());
                Account sender = new Account(from.getText());

                Email toSend = new Email(sender, reciver, subject.getText(), text.getText());

                toSend.writeEmail(reciver, subject.getText(), text.getText());

                // TODO: Re-think the email model (object to XML)
            }
        });
    }

    @FXML
    public void saveButton() {

    }

    @FXML
    public void deleteButton() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
