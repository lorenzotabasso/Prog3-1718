package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/*
La vista sia una tipica finestra di client di mail (es. Thunderbird), con funzionalità ridotte a quanto serve per:
 - vedere il nome dell'account di posta elettronica (che qui assumiamo fisso per l'applicazione, che non prevede
    autenticazione da parte dell'utente)
 - vedere la lista dei messaggi memorizzati nella casella di posta. La lista sia
    ordinata per data dai messaggi più recenti ai meno recenti
 - visualizzare un messaggio della casella di posta selezionandolo dalla lista dei messaggi
 - scrivere un messaggio e inviarlo a uno o più destinatari
 - rimuovere un messaggio dalla casella di posta elettronica e vedere la lista dei messaggi aggiornata.
 */

public class MainViewController implements Initializable, Observer {

    // UTILISSIMO https://stackoverflow.com/questions/40557492/mvc-with-javafx-and-fxml
    // SEGUIREMO L'APPROCCIO 1

    public MainViewController() {}

    @FXML // MainView components
    public TabPane root;
    public Button update;
    public Button write;
    public Button reply;
    public Button replyToAll;
    public Button forward;
    public Button delete;
    public Label status;
    public TitledPane mailboxName;
    public Label inbox;
    public Label drafts;
    public Label bin;


    // BUTTONS ---------------------------------------------------------------------------------------------------------

    /**
     * On click on Update button do something
     */
    @FXML
    public void updateButton() {
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                System.out.println(mess);
                status.setText(mess);
            }
        });
    }

    /**
     * On click on Write button do something
     */
    @FXML
    public void writeButton() {
        write.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                System.out.println(mess);
                onWriteClick();
            }
        });
    }

    /**
     * On click on Reply button do something
     */
    @FXML
    public void replyButton() {
        reply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                System.out.println(mess);
            }
        });
    }

    /**
     * On click on Reply To All button do something
     */
    @FXML
    public void replyToAllButton() {
        replyToAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                System.out.println(mess);
            }
        });
    }

    /**
     * On click on Forward button do something
     */
    @FXML
    public void forwardButton() {
        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                System.out.println(mess);
            }
        });
    }

    /**
     * On click on Delete label do something
     */
    @FXML
    public void deleteButton() {
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                System.out.println(mess);
            }
        });
    }

    /**
     * Sets the name of the account in the MainView
     */
    @FXML
    public void setMailboxName() {

    }

    // TODO: implementare lo stato nelle email, perche così possiamo discriminare se sono inbox, bin, drafts

    /**
     * On click on Inbox label do something
     */
    @FXML
    public void inboxLabel() {

    }

    /**
     * On click on Drafts label do something
     */
    @FXML
    public void draftsLabel() {

    }

    /**
     * On click on Bin label do something
     */
    @FXML
    public void binLabel() {

    }

    // INITIALIZING ----------------------------------------------------------------------------------------------------

    /**
     * It initialize all the event of the buttons
     */
    public void initializeButtons(){
        updateButton();
        writeButton();
        replyButton();
        replyToAllButton();
        forwardButton();
        deleteButton();
    }

    /**
     * It initialize the left VBox
     */
    private void initializeLeftVBox(){

    }

    /**
     * It call all the methods that initialize a category of components
     */
    private void initializeAll() {
        initializeButtons();
        initializeLeftVBox();
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
        System.out.println("GUI Loaded"); // DEBUG
    }

    /**
     * Implementation of update method in Observer interface
     * @param o:  the observable object.
     * @param arg: (optional) an argument passed to the notifyObservers method.
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    // ON CLICK --------------------------------------------------------------------------------------------------------

    /**
     * It opens a new Tab with WriteView loaded. It is used to write a new email.
     */
    private void onWriteClick(){
        try{
            Tab tab = new Tab("Write");
            tab.setContent(FXMLLoader.load(getClass().getResource("/view/WriteView.fxml"))); // load the GUI for the Write tab
            root.getTabs().add(tab); // Add the new tab beside the "Inbox" tab
            root.getSelectionModel().select(tab); // Switch to Write tab
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // POPULATING MAIN VIEW --------------------------------------------------------------------------------------------

    /**
     * It populates the main view with all the email with a specific status
     */
    private void populate() {

    }

} // end class
