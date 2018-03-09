package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

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
    public Pane root;
    public Label status;
    public Label email;
    public Button update;
    public Button write;
    public Button reply;
    public Button replyToAll;
    public Button forward;
    public Button delete;

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
     * On click on Update button do something
     */
    @FXML
    public void updateButton() {
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                status.setText(mess);
                System.out.println(mess);
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
                status.setText(mess);
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
                status.setText(mess);
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
                status.setText(mess);
                System.out.println(mess);
            }
        });
    }

    /**
     * On click on Delete button do something
     */
    @FXML
    public void deleteButton() {
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                status.setText(mess);
                System.out.println(mess);
            }
        });
    }

    /**
     * It initialize all the buttons link
     * @param location: The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources: The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtons();
        System.out.println("GUI Loaded"); // DEBUG
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    // LOADING NEW WINDOWS ---------------------------------------------------------------------------------------------

    /**
     * It opens a new WriteView inside the MainView
     */
    private void onWriteClick(){
        try{
            Pane writeWindow = FXMLLoader.load(getClass().getResource("/view/WriteView.fxml"));
            root.getChildren().setAll(writeWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
