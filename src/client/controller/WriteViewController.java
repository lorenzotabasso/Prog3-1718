package client.controller;

import client.task.SaveTask;
import client.task.SendTask;
import exception.ClientException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import client.model.Client;
import common.Email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class WriteViewController {

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

    public WriteViewController() {}

    // INITIALIZATION --------------------------------------------------------------------------------------------------

    /**
     * It initialize the WriteView populating all its section. Used in Mainview.
     *
     * @param exec the thread pool in which the Task will be executed.
     * @param clientModel the Client client.model .
     */
    public void init(ExecutorService exec, Client clientModel){
        this.exec = exec;
        this.clientModel = clientModel;

        initializeButtonsListeners();

        from.setText(clientModel.getUser().getUserEmail()); // Username already compiled for every new email.
    }

    /**
     * Overloaded version.
     * It initialize the WriteView populating the receiver field. Used in the ReadView.
     *
     * @param exec the thread pool in which the Task will be executed.
     * @param clientModel the Client client.model.
     * @param receiver the receiver to send the email.
     */
    public void init(ExecutorService exec, Client clientModel, String receiver){
        this.exec = exec;
        this.clientModel = clientModel;

        initializeButtonsListeners();

        from.setText(clientModel.getUser().getUserEmail()); // Username already compiled for every new email.
        to.setText(receiver);
    }

//    /**
//     * Overloaded version.
//     * It initialize the WriteView populating the receiver field. Used in the Mainview.
//     *
//     * @param exec the thread pool in which the Task will be executed.
//     * @param clientModel the Client client.model .
//     * @param whichFunction a string that makes the view change based on the function which initialized the controller.
//     *                      The possibilities are: "reply", "replyToAll" and "forward"
//     * @param toLoad email to show in the WriteView
//     */
//    public void init(ExecutorService exec, Client clientModel, String whichFunction, Email toLoad){
//        this.exec = exec;
//        this.clientModel = clientModel;
//
//        initializeButtonsListeners();
//
//        from.setText(clientModel.getUser().getUserEmail()); // Username already compiled for every new email.
//
//        switch (whichFunction) {
//            case "reply":
//                to.setText(toLoad.getSender());
//            case "replyToAll":
//                to.setText(toLoad.getSender());
//            case "forward":
//                to.setText("");
//        }
//
//        subject.setText("RE: " + toLoad.getSubject());
//    }

    /**
     * Overloaded version.
     * It initialize the WriteView populating the receiver field. Used in the Mainview.
     *
     * @param exec the thread pool in which the Task will be executed.
     * @param clientModel the Client client.model .
     * @param whichFunction a string that makes the view change based on the function which initialized the controller.
     *                      The possibilities are: "reply", "replyToAll" and "forward"
     * @param original email to show in the WriteView
     */
    public void initBasedOnFunction(ExecutorService exec, Client clientModel, String whichFunction, Email original){
        this.exec = exec;
        this.clientModel = clientModel;

        Email toLoad = original; // for safety

        initializeButtonsListeners();

        from.setText(clientModel.getUser().getUserEmail()); // Username already compiled for every new email.

        switch (whichFunction) {

            case "new":
                to.setText(toLoad.getSender());

            case "reply":

                // ci sono 2 casi:
                // 1) l'email ha un solo destinatario. --> rispondere solo a uno
                // 2) l'email ha più di un destinatario. --> rispondere solo a uno

                to.setText(toLoad.getSender());
                subject.setText("RE: " + toLoad.getSubject());
                text.setText("\n\n" + "[---------- Begin of original message ----------]" + "{ " + toLoad.toString() + "}");

            case "replyToAll":

                // ci sono 2 casi:
                // 1) l'email ha un solo destinatario. --> rispondere solo a uno
                // 2) l'email ha più di un destinatario. --> rispondere a tutti

                if (toLoad.getReceiver().size() == 1){
                    to.setText(toLoad.getSender());
                }
                else {
                    String finalsReceivers = "";
                    for (String newReceiver : toLoad.getReceiver()){
                        if (toLoad.getReceiver().get(0).equals(newReceiver)){
                            finalsReceivers.concat(newReceiver);
                        }
                        else {
                            finalsReceivers.concat("," + finalsReceivers.concat(newReceiver));
                        }
                    }
                    to.setText(finalsReceivers);
                }

                subject.setText("RE: " + toLoad.getSubject());
                text.setText("\n\n" + "[---------- Begin of original message ----------]" + "{ " + toLoad.toString() + "}");

            case "forward":
                to.setText("");
                subject.setText("RE: " + toLoad.getSubject());
                text.setText("\n\n" + "[---------- Begin of original message ----------]" + "{ " + toLoad.toString() + "}");

            case "editDraft":
                to.setText(toLoad.getText());
                subject.setText(toLoad.getSubject());
                text.setText(toLoad.getText());
        }
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

                boolean testTo = (to == null || to.getText().trim().isEmpty() || !to.getText().contains("@unito.it"));
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

                    exec.submit(new SendTask(clientModel, thisEmail)); // creo un thread che si occupa di inviare l'email.

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

                exec.submit(new SaveTask(clientModel, thisEmail)); // creo un thread che si occupa di inviare la bozza.

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