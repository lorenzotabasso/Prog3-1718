package client.controller;

import client.task.AuthTask;
import client.task.GetTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import client.model.Client;
import common.Email;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;

/* Dal Testo del progetto
La vista sia una tipica finestra di client di mail (es. Thunderbird), con funzionalità ridotte a quanto serve per:
 - vedere il nome dell'account di posta elettronica (che qui assumiamo fisso per l'applicazione, che non prevede
    autenticazione da parte dell'utente)
 - vedere la lista dei messaggi memorizzati nella casella di posta. La lista sia
    ordinata per emails dai messaggi più recenti ai meno recenti
 - visualizzare un messaggio della casella di posta selezionandolo dalla lista dei messaggi
 - scrivere un messaggio e inviarlo a uno o più destinatari
 - rimuovere un messaggio dalla casella di posta elettronica e vedere la lista dei messaggi aggiornata.
 */

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class MainViewController {

    // UTILISSIMO https://stackoverflow.com/questions/40557492/mvc-with-javafx-and-fxml
    // SEGUIREMO L'APPROCCIO 1

    // MainView components

    @FXML
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
    public Button editDraft;

    @FXML
    public Label status;

    @FXML
    public TreeView<String> folders;

    @FXML
    public TableView<Email> table;

    @FXML
    public TableColumn subject;

    @FXML
    public TableColumn to;

    @FXML
    public TableColumn from;

    @FXML
    public TableColumn date;

    private Client clientModel;
    private ExecutorService exec;
    private Email selectedEmail; // needed for reply and forward
    private String selectedCategory;

    public MainViewController() {}

    // INITIALIZATION --------------------------------------------------------------------------------------------------

    /**
     * It initialize the MainView populating all its section.
     *
     * @param exec the thread pool in which the Task will be executed
     * @param clientModel the Client client.model
     */
    public void init(ExecutorService exec, Client clientModel){
        this.exec = exec;
        this.clientModel = clientModel;

        initializeButtonsListeners();
        initTree();

        clientModel.setStatusProperty("loading...");
        status.textProperty().bind(clientModel.getStatus());

        exec.execute(new AuthTask(clientModel));

        // CASI
        /*
        1) read, read OK
        2) read, load NOOK
        3) load, read NOOK, all'avvio carica le bozze
        4) load, load NOOK, all'avvio carica le bozze
         */

        // CASI
        /*
        1) refresh, refresh NOOK
        2) refresh, no refresh NOOK
        3) no refresh, no refresh
        4) no refresh, refresh
         */

    }

    // EVENT HANDLERS INITIALIZATION -----------------------------------------------------------------------------------

    /**
     * It initialize all the event handlers of the buttons.
     */

    @FXML
    private void initializeButtonsListeners(){

        // UPDATE
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //table.refresh();

                exec.submit(new AuthTask(clientModel));
                exec.submit(new GetTask(clientModel));

                if (selectedCategory != null) loadEmails(selectedCategory);
                else table.refresh();

            }
        });

        // WRITE
        write.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) { openWriteTab(); }
        });

        // REPLY
        reply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                selectedEmail = table.getSelectionModel().getSelectedItem();
                openWriteTab("reply", selectedEmail); // it opens the email object associated to the selected row in another tab.
            }
        });

        // REPLY TO ALL
        replyToAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                selectedEmail = table.getSelectionModel().getSelectedItem();
                openWriteTab("replyToAll", selectedEmail); // it opens the email object associated to the selected row in another tab.
            }
        });

        // FORWARD
        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                selectedEmail = table.getSelectionModel().getSelectedItem();
                openWriteTab("forward", selectedEmail);
            }
        });

        // EDIT DRAFT
        editDraft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                selectedEmail = table.getSelectionModel().getSelectedItem();
                if (selectedEmail != null) openWriteTab("editDraft", selectedEmail);

            }
        });

        editDraft.setDisable(true); // set the edit draft button as disabled from GUI start

    }

    /**
     * It populates the TreeView with 3 child and it attach to them a ChangeListener.
     */
    @FXML
    private void initTree() {

        // Initializing the tree nodes
        final TreeItem<String> root = new TreeItem<>(clientModel.getUser().getUserEmail() + ":");
        root.setExpanded(true);
        root.getChildren().add(new TreeItem<>("Inbox"));
        root.getChildren().add(new TreeItem<>("Sent"));
        root.getChildren().add(new TreeItem<>("Drafts"));
        folders.setRoot(root);
        //folders.getSelectionModel().select(1);

        // adding listener to each tree node //todo tipo di listener
        folders.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(
                    ObservableValue<? extends TreeItem<String>> observable,
                    TreeItem<String> old_val, TreeItem<String> new_val) {

                switch (new_val.getValue()) {
                    case "Sent":
                        editDraft.setDisable(true);
                        selectedCategory = "o";
                        loadEmails("o");
                        break;
                    case "Drafts":
                        editDraft.setDisable(false);
                        selectedCategory = "d";
                        loadEmails("d");
                        break;
                    case "Inbox":
                        editDraft.setDisable(true);
                        selectedCategory = "i";
                        loadEmails("i");
                        break;
                    default: // Inbox
                        editDraft.setDisable(true);
                        selectedCategory = "i";
                        loadEmails("i");
                        break;
                } // end switch
            } // end changed() definition
        }); // end ChangeListener
    } // end initTree

    // POPULATING ------------------------------------------------------------------------------------------------------

    /**
     * It populates the MainView with all the email with a specific status. It reads all the email serialized files
     * and it updates the MainViewTable.
     *
     * @param location a flag which specifies the location for reading the files
     */
    private void loadEmails(String location) {

        date.setSortType(TableColumn.SortType.DESCENDING);

        //table.refresh(); // da testare

        switch (location){
            case "i":
                clientModel.read("i");
                //table.refresh();
                table.setItems(clientModel.getInbox());
                break;

            case "o":
                //table.refresh();
                clientModel.read("o");
                table.setItems(clientModel.getOutbox());
                break;

            case "d":
                //table.refresh();
                clientModel.read("d");
                table.setItems(clientModel.getDraft());
                break;

            default: // same as inbox
                clientModel.read("i");
                //table.refresh();
                table.setItems(clientModel.getInbox());
                break;
        }

        table.getSortOrder().add(date);

        table.setRowFactory(tv -> {
            TableRow<Email> row = new TableRow<>();

            // Double click on row opens email in other tab
            // more infos: https://stackoverflow.com/questions/26563390/detect-doubleclick-on-row-of-tableview-javafx

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Email rowData = row.getItem();
                    openReadTab(rowData); // it opens the email object associated to the selected row in another tab.
                }
            });

            row.itemProperty().addListener((obs, oldValue, newRowValue) -> {
                if (newRowValue != null) {
                    if (newRowValue.getSeen()) { // Test: tolo il ! per un momento
                        row.setStyle("-fx-font-weight: bold");
                    } else {
                        row.setStyle(" ");
                    }
                    table.refresh();
                }
            });

            return row;
        });
    }

    public void loadAllEmails(){
        loadEmails("i");
        loadEmails("o");
        loadEmails("d");
    }

    public void getCategory(){

    }

    // SUPPORT ---------------------------------------------------------------------------------------------------------

    /**
     * It opens a new ReadView Tab and loads into it the email object passed as parameter.
     *
     * @param toShow email to load in the ReadView.
     */
    private void openReadTab(Email toShow) {
        try{
            Tab tab = new Tab(toShow.getSubject());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/view/ReadView.fxml"));

            tab.setContent(fxmlLoader.load());

            //ReadViewController readViewController =  new ReadViewController(); // in caso d'emergenza, questa riga funge
            ReadViewController readViewController =  fxmlLoader.getController();

            readViewController.init(this.exec, this.clientModel, toShow);

            root.getTabs().add(tab); // Add the new tab beside the "Inbox" tab
            root.getSelectionModel().select(tab); // Switch to Write tab
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
            WriteViewController writeViewController =  fxmlLoader.getController();

            writeViewController.init(this.exec, this.clientModel);

            root.getTabs().add(tab); // Add the new tab beside the "Inbox" tab
            root.getSelectionModel().select(tab); // Switch to Write tab
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * it opens a new WriteView. It's invoked after pressing the buttons Reply, Reply to all and Forward.
     *
     * @param whichFunction a string that makes the view change based on the function which initialized the controller.
     *                      The possibilities are: "reply", "replyToAll" and "forward"
     * @param original email to show in the WriteView
     */
    private void openWriteTab(String whichFunction, Email original) {

        Email toLoad = original; // for safety

        try {
            // it opens the email object associated to the selected row in another tab.
            Tab tab = new Tab("Reply to " + toLoad.getSubject());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/view/WriteView.fxml"));


            tab.setContent(fxmlLoader.load());

            //WriteViewController readViewController =  new WriteViewController(); // in caso d'emergenza, questa riga funge
            WriteViewController writeViewController =  fxmlLoader.getController();

            writeViewController.initBasedOnFunction(this.exec, this.clientModel, whichFunction, toLoad);

            root.getTabs().add(tab); // Add the new tab beside the "Inbox" tab
            root.getSelectionModel().select(tab); // Switch to Write tab
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readAll(){
        clientModel.read("i");
        clientModel.read("d");
        clientModel.read("o");
    }

} // end class
