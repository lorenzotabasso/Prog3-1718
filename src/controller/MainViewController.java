package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Account;
import model.Email;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    public TableView<Email> table;


    // INITIALIZING ----------------------------------------------------------------------------------------------------

    /**
     * Sets the name of the account in the MainView
     */
    @FXML
    public void setMailboxName() {

    }

    // TODO: implementare lo stato nelle email, perche così possiamo discriminare se sono inbox, bin, drafts

    /**
     * It initialize all the event handlers of the buttons
     */
    private void initializeButtons(){

        // UPDATE
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                System.out.println(mess);
                status.setText(mess);
            }
        });

        // WRITE
        write.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                openTab("Write", "/view/WriteView.fxml");
            }
        });

        // REPLY
        reply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                System.out.println(mess);
            }
        });

        // REPLY TO ALL
        replyToAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                System.out.println(mess);
            }
        });

        // FORWARD
        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                System.out.println(mess);
            }
        });

        // DELETE
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                System.out.println(mess);
            }
        });

        // STATUS (Label)
        status.setText();

        // INBOX (Label)

        // DRAFTS (Label)

        // BIN (Label)
    }

    /**
     * It populates the main view with all the email with a specific status
     */
    private void loadEmails() {

        // TODO: to finish this method, we need to divide email by status

        // utility for columns https://docs.oracle.com/javafx/2/fxml_get_started/fxml_tutorial_intermediate.htm

        readFile();

        Account sender = new Account("test@test.it");
        Account receiver = new Account("a@test.it");

        ObservableList<Email> data = table.getItems();
        data.add(new Email(sender, receiver, "Another Test", "Test N"));

        // Double click on row - https://stackoverflow.com/questions/26563390/detect-doubleclick-on-row-of-tableview-javafx

        table.setRowFactory( tv -> {
            TableRow<Email> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Email rowData = row.getItem();
                    System.out.println(rowData.toString());
                    openTab("Read", "/view/ReadView.fxml");
                }
            });
            return row ;
        });


    }

    /**
     * It calls all the methods that initialize a category of components
     */
    private void initializeAll() {
        initializeButtons();
        //loadEmails();
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

    // SUPPORT ---------------------------------------------------------------------------------------------------------

    public void readFile() {
        Email e; // Needed for printing values
        try {
            FileInputStream fileIn = new FileInputStream("/Volumes/HDD/Lorenzo/Unito/3 Anno/Prog3/Progetto/prog3-project-1718/src/data/emails/email.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (Email) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Email class not found");
            c.printStackTrace();
            return;
        }

        System.out.println("Deserialized Email...");
        System.out.println("ID: " + e.getIdEmail());
        System.out.println("Subject: " + e.getSubject());
        System.out.println("Receiver: " + e.getReceiver());
        System.out.println("Sender: " + e.getSender());
        System.out.println("Text: " + e.getText());
        System.out.println("Date: " + e.getDate());
    }

    public void openTab(String title, String pathToFXML) {
        try{
            Tab tab = new Tab(title);
            tab.setContent(FXMLLoader.load(getClass().getResource(pathToFXML))); // load the GUI for the Write tab
            root.getTabs().add(tab); // Add the new tab beside the "Inbox" tab
            root.getSelectionModel().select(tab); // Switch to Write tab
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

} // end class
