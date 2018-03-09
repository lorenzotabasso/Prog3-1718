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
import java.util.ResourceBundle;

public class MainViewController implements Initializable{

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButtons();
        System.out.println("GUI Loaded"); // DEBUG
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
