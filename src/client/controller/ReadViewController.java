package client.controller;

import client.task.DeleteTask;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import client.model.Client;
import common.Email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class ReadViewController implements Observer {

    // ReadView Components
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
    private Email thisEmail;

    public ReadViewController() {}

    // INITIALIZATION --------------------------------------------------------------------------------------------------

    /**
     * It initialize the ReadView populating all its section
     *
     * @param exec the thread pool in which the Task will be executed.
     * @param clientModel the Client client.model.
     * @param emailToShow email to show in the ReadView.
     */
    public void init(ExecutorService exec, Client clientModel, Email emailToShow){
        this.exec = exec;
        this.clientModel = clientModel;
        this.thisEmail = emailToShow;

        initializeButtonsListeners();
        initializeEmailContent();

    }

    // EVENT HANDLERS INITIALIZATION -----------------------------------------------------------------------------------

    /**
     * It initialize all the event handlers of the buttons.
     */
    private void initializeButtonsListeners() {

        // REPLY
        reply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (thisEmail.getReceiver().size() == 1) {

                    // there's only one receiver
                    openWriteTab();
                }
                else {
                    // there are more than one receivers
                    openWriteTab(thisEmail);
                }
            }
        });

        // REPLY TO ALL
        replyToAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openWriteTab(thisEmail);
            }
        });

        // FORWARD
        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Email toLoad = thisEmail;
                toLoad.setReceiver(new ArrayList<>()); // because the user will choice the new receiver

                openWriteTab(toLoad);
            }
        });

        // DELETE
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //clientModel.delete(thisEmail, "i"); // TODO: provvisorio. Implementare il metodo getLocation() in Client
                exec.submit(new DeleteTask(clientModel, thisEmail));
                closeTab();
            }
        });
    }

    // POPULATING ------------------------------------------------------------------------------------------------------

    /**
     * It initialize all the text content of the ReadView.
     */
    private void initializeEmailContent() {
        to.setText(thisEmail.getReceiver().toString()); // toString needed because there can be more than one receiver
        from.setText(thisEmail.getSender());
        subject.setText(thisEmail.getSubject());
        text.setText(thisEmail.getText());
    }

    // IMPLEMENTATIONS -------------------------------------------------------------------------------------------------

    /**
     * Implementation of update method in Observer interface.
     *
     * @param o the observable object.
     * @param arg (optional) an argument passed to the notifyObservers method.
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

    /**
     * It opens a new WriteView Tab.
     */
    private void openWriteTab() {
        try{
            Tab tab = new Tab("New Email");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/view/WriteView.fxml"));

            tab.setContent(fxmlLoader.load());

            //WriteViewController readViewController =  new WriteViewController(); // in caso d'emergenza, questa riga funge
            WriteViewController readViewController =  fxmlLoader.getController();

            readViewController.init(exec, clientModel, thisEmail.getSender());

            TabPane inboxTab = findEnclosingTabPane(root);
            inboxTab.getTabs().add(tab); // Add the new tab beside the "Read" tab
            inboxTab.getSelectionModel().select(tab); // Switch to Write tab
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Overloaded Version. It opens a new WriteView Tab.
     */
    private void openWriteTab(Email toLoad) {
        try{
            Tab tab = new Tab("Reply to " + toLoad.getSubject());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/view/WriteView.fxml"));

            tab.setContent(fxmlLoader.load());

            //WriteViewController readViewController =  new WriteViewController(); // in caso d'emergenza, questa riga funge
            WriteViewController writeViewController =  fxmlLoader.getController();

            writeViewController.init(this.exec, this.clientModel, toLoad);

            TabPane inboxTab = findEnclosingTabPane(root);
            inboxTab.getTabs().add(tab); // Add the new tab beside the "Read" tab
            inboxTab.getSelectionModel().select(tab); // Switch to Write tab
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

} // end class
