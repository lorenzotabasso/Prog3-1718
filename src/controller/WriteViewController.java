package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Account;
import model.Client;
import model.Email;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class WriteViewController implements Initializable, Observer {

    public WriteViewController() {

    }

    @FXML
    public VBox root;
    public TextField to;
    public TextField from;
    public TextField subject;
    public TextArea text;
    public Button send;
    public Button saveAsDraft;
    public Button delete;

    //private client
    // private threadpool

    // TODO: add init method here, to initialize both client reference and the threadpool, as the following template

    /*
    public void init(ExecutorService exec, ClientModel model) {
        this.exec = exec;
        this.model = model;
    }
    */

    // INITIALIZING ----------------------------------------------------------------------------------------------------

    /**
     * It initialize all the event handlers of the buttons
     */
    private void initializeButtons() {

        // SEND
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Account reciver = new Account(to.getText());
                Account sender = new Account(from.getText());

                Email toSend = new Email(sender, reciver, subject.getText(), text.getText());

                toSend.writeEmail(reciver, subject.getText(), text.getText());

                // Is not necessary to set the state, because, when a new Email is created, it has already the
                // state of new (2). For further information, see Email constructor.

                closeTab();
            }
        });

        // SAVE
        saveAsDraft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Account reciver = new Account(to.getText());
                Account sender = new Account(from.getText());

                Email toSend = new Email(sender, reciver, subject.getText(), text.getText());
                toSend.setState(0); // the email is a draft
                closeTab();
            }
        });

        // DELETE
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Account reciver = new Account(to.getText());
                Account sender = new Account(from.getText());

                Email toSend = new Email(sender, reciver, subject.getText(), text.getText());
                toSend.setState(-1);
                toSend.writeFile(); // TODO: better implementation of writeFile method

                closeTab();
            }
        });
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
     *
     * @param location:  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources: The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeAll();
    }

    /**
     * Implementation of update method in Observer interface
     *
     * @param o:   the observable object.
     * @param arg: (optional) an argument passed to the notifyObservers method.
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    // SUPPORT ---------------------------------------------------------------------------------------------------------

    /**
     * It iterate until it founds the Tab element to be closed.
     * Used by closeTab().
     *
     * @param n the node in which we will start the search
     * @return the final Tab to be closed
     */
    private static TabPane findEnclosingTabPane(Node n) {
        while (n != null && !(n instanceof TabPane)) {
            n = n.getParent();
        }
        return (TabPane) n;
    }

    /**
     * It find the tab to be closed and it close it.
     */
    private void closeTab() {
        TabPane tabPane = findEnclosingTabPane(root);
        tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
    }

} // end class