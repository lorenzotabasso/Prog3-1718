package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

                // Is not necessary to set the state, because, when a new Email is created, it has already the
                // state of new (2). For further information, see Email constructor.
            }
        });
    }

    @FXML
    public void saveButton() {
        saveAsDraft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Account reciver = new Account(to.getText());
                Account sender = new Account(from.getText());

                Email toSend = new Email(sender, reciver, subject.getText(), text.getText());

                toSend.setState(0); // the email is a draft
            }
        });
    }

    @FXML
    public void deleteButton() {
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Account reciver = new Account(to.getText());
                Account sender = new Account(from.getText());

                Email toSend = new Email(sender, reciver, subject.getText(), text.getText());
            }
        });
    }

    // INITIALIZING ----------------------------------------------------------------------------------------------------

    /**
     * It initialize all the event of the buttons
     */
    private void initializeButtons(){
        sendButton();
        saveButton();
        deleteButton();
    }

    /**
     * It call all the methods that initialize a category of components
     */
    private void initializeAll() {
        initializeButtons();

    }

    // IMPLEMENTATIONS -------------------------------------------------------------------------------------------------

    /**
     * It initialize all the necessary for the GUI
     * @param location: The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources: The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeAll();
    }

    /**
     * Implementation of update method in Observer interface
     * @param o:  the observable object.
     * @param arg: (optional) an argument passed to the notifyObservers method.
     */
    @Override
    public void update(Observable o, Object arg) {

    }
}
