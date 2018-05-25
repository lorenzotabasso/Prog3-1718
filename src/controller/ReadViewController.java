package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import model.Client;
import model.Email;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 */

public class ReadViewController implements Observer {

    public ReadViewController() {}

    @FXML
    public VBox root;

    @FXML
    public Label to;

    @FXML
    public Label from;

    @FXML
    public Label subject;

    @FXML
    public Label text;

    @FXML
    public Button reply;

    @FXML
    public Button replyToAll;

    @FXML
    public Button forward;

    @FXML
    public Button delete;

    private Client clientModel;
    private ExecutorService exec;
    private Email email;

    // INITIALIZING ----------------------------------------------------------------------------------------------------

    /**
     * It initialize all the event handlers of the buttons
     */
    private void initializeButtonsListeners() {

        // REPLY
        reply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        // REPLY TO ALL
        replyToAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        // FORWARD
        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        // DELETE
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // something ...

                closeTab();
            }
        });
    }

    /**
     * It initialize all the text content of the ReadView
     */
    private void initializeContent() {
        to.setText(email.getReceiver());
        from.setText(email.getSender());
        subject.setText(email.getSubject());
        text.setText(email.getText());
    }

    // OTHER -----------------------------------------------------------------------------------------------------------

    /**
     * It initialize the ReadView populating all its section
     * @param exec: the thread pool in which the Task will be executed
     * @param clientModel: the Client model
     * @param emailToShow email to show in the ReadView
     */
    public void init(ExecutorService exec, Client clientModel, Email emailToShow){
        System.out.println("Init done"); // DEBUG

        this.exec = exec;
        this.clientModel = clientModel;
        this.email = emailToShow;

        initializeButtonsListeners();
        initializeContent();

    }

    // IMPLEMENTATIONS -------------------------------------------------------------------------------------------------

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
     * It iterate until it founds the Tab element to be closed. Used by closeTab().
     *
     * @param n the node in which we will start the search
     * @return the final Tab to be closed
     * @see #closeTab()
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
