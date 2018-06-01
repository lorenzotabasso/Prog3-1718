package client.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import client.model.Client;
import client.model.Email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 */

public class WriteViewController implements Observer {

    public WriteViewController() {

    }

    @FXML
    public VBox root;

    @FXML
    public TextField to;

    @FXML
    public TextField from;

    @FXML
    public TextField subject;

    @FXML
    public TextArea text;

    @FXML
    public Button send;

    @FXML
    public Button saveAsDraft;

    @FXML
    public Button delete;

    private Client clientModel;
    private ExecutorService exec;

    // INITIALIZATION --------------------------------------------------------------------------------------------------

    /**
     * It initialize the WriteView populating all its section. Used in the MainView
     * @param exec: the thread pool in which the Task will be executed
     * @param clientModel: the Client client.model
     */
    public void init(ExecutorService exec, Client clientModel){
        this.exec = exec;
        this.clientModel = clientModel;

        initializeButtonsListeners();

        from.setText(clientModel.getUser().getUserEmail()); // Username already compiled for every new email.
    }

    /**
     * Overloaded version.
     * It initialize the WriteView populating all its section. Used in the ReadView
     * @param exec: the thread pool in which the Task will be executed
     * @param clientModel: the Client client.model
     */
    public void init(ExecutorService exec, Client clientModel, String receiver){
        this.exec = exec;
        this.clientModel = clientModel;

        initializeButtonsListeners();

        from.setText(clientModel.getUser().getUserEmail()); // Username already compiled for every new email.
        to.setText(receiver);
    }

    // EVENT HANDLERS INITIALIZATION -----------------------------------------------------------------------------------

    /**
     * It initialize all the event handlers of the buttons
     */
    private void initializeButtonsListeners() {

        // SEND
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                boolean testTo = (to == null || to.getText().trim().isEmpty());
                boolean testFrom = (from == null || from.getText().trim().isEmpty());
                boolean testSubject = (subject == null || subject.getText().trim().isEmpty());

                // if Email is empty, alerts the user and close the Tab
                if (testTo || testFrom || testSubject) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Unable to send this email.");
                    alert.setContentText("Some essential fields like 'to', 'from' or 'subject' are missing. Check it out and try again!");
                    alert.showAndWait();
                }
                else {
                    ArrayList<String> receiver = new ArrayList<>();
                    String[] output = to.getText().split(",");
                    receiver.addAll(Arrays.asList(output));

                    Email thisEmail = new Email(from.getText(), receiver, subject.getText(), text.getText());
                    clientModel.write(thisEmail, "o"); // Usiamo "i" a scopo di DEBUG, in realtà sarebbe "o"
                    closeTab();
                }
            }
        });

        // SAVE AS DRAFT
        saveAsDraft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<String> receiver = new ArrayList<>();
                String[] output = to.getText().split(",");
                receiver.addAll(Arrays.asList(output));

                Email thisEmail = new Email(from.getText(), receiver, subject.getText(), text.getText());
                clientModel.write(thisEmail, "d"); // Usiamo "i" a scopo di DEBUG, in realtà sarebbe "d"
                closeTab();
            }
        });

        // DELETE
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                closeTab();
            }
        });
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