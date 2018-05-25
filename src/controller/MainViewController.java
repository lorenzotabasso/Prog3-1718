package controller;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.Account;
import model.Client;
import model.Email;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

/* Dal Testo del progetto
La vista sia una tipica finestra di client di mail (es. Thunderbird), con funzionalità ridotte a quanto serve per:
 - vedere il nome dell'account di posta elettronica (che qui assumiamo fisso per l'applicazione, che non prevede
    autenticazione da parte dell'utente)
 - vedere la lista dei messaggi memorizzati nella casella di posta. La lista sia
    ordinata per data dai messaggi più recenti ai meno recenti
 - visualizzare un messaggio della casella di posta selezionandolo dalla lista dei messaggi
 - scrivere un messaggio e inviarlo a uno o più destinatari
 - rimuovere un messaggio dalla casella di posta elettronica e vedere la lista dei messaggi aggiornata.
 */

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 */

public class MainViewController implements Initializable, Observer {

    // UTILISSIMO https://stackoverflow.com/questions/40557492/mvc-with-javafx-and-fxml
    // SEGUIREMO L'APPROCCIO 1

    private Client clientModel;
    private ExecutorService exec;

    public MainViewController() {}

    @FXML // MainView components
    public TabPane root;

    @FXML
    public Button update;

    @FXML
    public Button write;

    @FXML
    public Button reply;

    @FXML
    public Button replyToAll;

    @FXML
    public Button forward;

    @FXML
    public Button delete;

    @FXML
    public Label status;

    @FXML
    public TitledPane mailboxName;

    @FXML
    public TreeView<String> folders;

    @FXML
    public TableView<Email> table;

    @FXML
    public TableColumn subject;

    @FXML
    public TableColumn from;

    @FXML
    public TableColumn date;


    // INITIALIZING ----------------------------------------------------------------------------------------------------

    /**
     * Sets the name of the account in the MainView
     */
    @FXML
    public void setMailboxName() {

    }

    /**
     * It initialize all the event handlers of the buttons
     *
     * @see #initialize(URL, ResourceBundle)
     */

    @FXML
    private void initializeButtonsListeners(){

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
                //openTab("Write", "/view/WriteView.fxml");
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
        //status.setText();


    }

    /**
     * It attach 4 listeners to the corresponding category in the Treewiew.
     *
     * @see #initialize(URL, ResourceBundle)
     */
    @FXML
    private void initTreeListeners() {

        // INBOX (Label)

        // OUTBOX

        // DRAFTS (Label)


        /* Detect selection (ChangeListener) */
        folders = new TreeView<String>();
        TreeView<String> root = new TreeView<String>(new TreeItem<String>("root"));
        //TreeView<String> inbox = new TreeView<String>(new TreeItem<String>("inbox"));

        TreeItem<String> newRoot = new TreeItem<String>("Root");
        TreeItem<String> inbox = new TreeItem<String>("inbox");

        newRoot.getChildren().add(inbox);

        folders.setRoot(newRoot);


    }

    // POPULATING ------------------------------------------------------------------------------------------------------

    /**
     * It populates the MainView with all the email with a specific status
     *
     * @see #initialize(URL, ResourceBundle)
     */
    private void loadEmails() {

        // TODO: da finire

        // utility for columns https://docs.oracle.com/javafx/2/fxml_get_started/fxml_tutorial_intermediate.htm

        // TEST
        //Email em = new Email(new Account("Sender"), new Account("Receiver"), "SERIALIZZAMI333", "PROVA DI TESTO SERIALIZZATO");
        //model.write(em, "i");

        // reading serialized files and updating MainViewTable
        clientModel.read("i");

        table.setItems(clientModel.getInbox());

        // Double click on row opens email in other tab
        // (https://stackoverflow.com/questions/26563390/detect-doubleclick-on-row-of-tableview-javafx)

        table.setRowFactory( tv -> {
            TableRow<Email> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Email rowData = row.getItem();

                    // System.out.println(rowData.toString()); // DEBUG

                    openReadTab(rowData, "src/view/ReadView.fxml");

                    // TODO: necessario avare riferimenti ai 2 controller read e write
                }
            });
            return row ;
        });


    }

    /**
     * It populates the tree of the MainView.
     *
     * @see #initialize(URL, ResourceBundle)
     */
    private void loadTree() { // TODO: Da problemi, è da sistemare
        TreeItem<String> root = new TreeItem<>("Account: " + clientModel.getUser().getEmail());
        root.setExpanded(true);
        root.getChildren().add(new TreeItem<>("Inbox"));
        root.getChildren().add(new TreeItem<>("Sent"));
        root.getChildren().add(new TreeItem<>("Drafts"));
        root.getChildren().add(new TreeItem<>("Bin"));
        folders.setRoot(root);
        folders.getSelectionModel().select(1);
    }

    // IMPLEMENTATIONS -------------------------------------------------------------------------------------------------

    /**
     * It initialize all FXML annotated components of the MainView
     * @param location: The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources: The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtonsListeners();

        //loadTree();
        //initTreeListeners();

        System.out.println("FXNL annotated components Loaded"); // DEBUG
    }

    /**
     * It initialize the MainView populating all its section
     * @param exec: the thread pool in which the Task will be executed
     * @param clientModel: the Client model
     */
    public void init(ExecutorService exec, Client clientModel){
        this.exec = exec;
        this.clientModel = clientModel;

        status.textProperty().bind(clientModel.getStatus());
        clientModel.setStatusProperty("loading...");

        loadEmails();

        // TODO: implementare threads. esempio: controllare nuve email allo startup, sincronizzare le email tra client e server.
    }

    /**
     * Implementation of update method in Observer interface
     * @param o:  the observable object.
     * @param arg: (optional) an argument passed to the notifyObservers method.
     */
    @Override
    public void update(Observable o, Object arg) { // TODO: a cosa serve l'oggetto arg? capirlo.

    }

    // SUPPORT ---------------------------------------------------------------------------------------------------------

    /**
     * It opens a new ReadView Tab
     * @param toShow email to load in the ReadView
     */
    private void openReadTab(Email toShow, String path) {
        try{
            Tab tab = new Tab(toShow.getSubject());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ReadView.fxml"));

            tab.setContent(fxmlLoader.load());

            //ReadViewController readViewController =  new ReadViewController(); // in caso d'emergenza, questa riga funge
            ReadViewController readViewController =  fxmlLoader.getController();

            readViewController.init(exec, clientModel, toShow);

            root.getTabs().add(tab); // Add the new tab beside the "Inbox" tab
            root.getSelectionModel().select(tab); // Switch to Write tab
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


} // end class
