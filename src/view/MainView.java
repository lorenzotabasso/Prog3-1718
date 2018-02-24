package view;

import controller.MainViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

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

public class MainView implements Observer {

    // UTILISSIMO https://stackoverflow.com/questions/40557492/mvc-with-javafx-and-fxml
    // SEGUIREMO L'APPROCCIO 2

    private MainViewController controller;
    private Parent root = null; // needed for find the buttons in the FXML file

    /**
     * Costructor. It initialize the controller and all the button's listener
     */
    public MainView(MainViewController c){
        initializeController(c);
        initializeRoot();
        initializeUpdateButton();
        initializeWriteButton();
        initializeReplyButton();
        initializeReplyToAllButton();
        initializeForwardButton();
        initializeDeleteButton();
    }

    public void initializeController(MainViewController c) {
        this.controller = c;
    }

    /**
     * It initialize 'root' parameter, used for find the buttons in the FXML file
     */
    private void initializeRoot() {
        try {
            root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * it initialize the listener for the 'update' button
     */
    private void initializeUpdateButton(){
        Button update = (Button) root.lookup("#update"); // #button exists in FXMLDocument.fxml
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //label.setText("Accepted");
                System.out.println("UPDATE");
            }
        });
    }

    /**
     * it initialize the listener for the 'write' button
     */
    private void initializeWriteButton(){
        Button write = (Button) root.lookup("#write"); // #button exists in FXMLDocument.fxml
        write.setOnAction(e -> controller.writeButton(e));
    }

    /**
     * it initialize the listener for the 'reply' button
     */
    private void initializeReplyButton(){
        Button reply = (Button) root.lookup("#reply"); // #button exists in FXMLDocument.fxml
        reply.setOnAction(e -> controller.replyButton(e));
    }

    /**
     * it initialize the listener for the 'reply to all' button
     */
    private void initializeReplyToAllButton(){
        Button replyToAll = (Button) root.lookup("#replyToAll"); // #button exists in FXMLDocument.fxml
        replyToAll.setOnAction(e -> controller.replyToAllButton(e));
    }

    /**
     * it initialize the listener for the 'forward' button
     */
    private void initializeForwardButton(){
        Button forward = (Button) root.lookup("#forward"); // #button exists in FXMLDocument.fxml
        forward.setOnAction(e -> controller.forwardButton(e));
    }

    /**
     * it initialize the listener for the 'delete' button
     */
    private void initializeDeleteButton(){
        Button delete = (Button) root.lookup("#delete"); // #button exists in FXMLDocument.fxml
        delete.setOnAction(e -> controller.deleteButton(e));
    }

    public Parent getRoot() {
        return root;
    }

    public void update(Observable ob, Object extra_args){}

}
