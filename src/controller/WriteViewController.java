package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Account;

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

    // private client
    // private threadpool

    // TODO: Aggiungere medoto init qui sotto, per inizializzare una riferimento al client e un Threadpool, come il template qui in basso

    /*
    public void init(ExecutorService exec, ClientModel model) {
        this.exec = exec;
        this.model = model;
    }
    */

    // INITIALIZING ----------------------------------------------------------------------------------------------------

    /**
     * It initialize all the event handlers of the buttons
     *
     * @see #initialize(URL, ResourceBundle)
     */
    private void initializeButtonsListeners() {

        // SEND
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Account reciver = new Account(to.getText());
                Account sender = new Account(from.getText());

                // TODO: Aggiungere scrittura su file dell'email, usare il metodo write alla riga 236 del Client (model.Client)

                // Email toSend = new Email(sender, reciver, subject.getText(), text.getText());
                //toSend.setState(2); // Non necessario, perchè quando un'email viene creata, viene già settata a 2 (nuovo)
                                      // Per maggiori informazioni vedi il costruttore di Email
                // toSend.writeFile(); // Usare write()

                closeTab();
            }
        });

        // SAVE
        saveAsDraft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Account reciver = new Account(to.getText());
                Account sender = new Account(from.getText());

                // TODO: Aggiungere scrittura su file dell'email, usare il metodo write alla riga 236 del Client (model.Client)

                // Email toSend = new Email(sender, reciver, subject.getText(), text.getText());
                //toSend.setState(0); // settare lo stato dell'email! (0 = bozza)
                // toSend.writeFile(); // Usare write()

                closeTab();
            }
        });

        // DELETE
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Account reciver = new Account(to.getText());
                Account sender = new Account(from.getText());

                // TODO: Aggiungere scrittura su file dell'email, usare il metodo write alla riga 236 del Client (model.Client)

                // Email toSend = new Email(sender, reciver, subject.getText(), text.getText());
                //toSend.setState(-1); // settare lo stato dell'email! (-1 = eliminata)
                // toSend.writeFile(); // Usare write()

                closeTab();
            }
        });
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
        initializeButtonsListeners();
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