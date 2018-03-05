package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class MainViewController {

    public MainViewController() {}

    @FXML // MainView components
    public Label status;
    public Label email;
    public Button update;
    public Button write;
    public Button reply;
    public Button replyToAll;
    public Button forward;
    public Button delete;

    /**
     * It initialize the image of each button
     */
    public void setGraphics() {
        // TO TEST
        Image test = new Image(getClass().getResourceAsStream("img/update.png"));
    }

    @FXML
    public void updateButton(ActionEvent event) {
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
    public void writeButton(ActionEvent event) {
        write.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                status.setText(mess);
                System.out.println(mess);
            }
        });
    }

    @FXML
    public void replyButton(ActionEvent event) {
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
    public void replyToAllButton(ActionEvent event) {
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
    public void forwardButton(ActionEvent event) {
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
    public void deleteButton(ActionEvent event) {
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String mess = "You clicked: " + e.getSource() + "!";
                status.setText(mess);
                System.out.println(mess);
            }
        });
    }

    public void initialize() {
        System.out.println("GUI Loaded"); // DEBUG
    }


    // LOADING NEW WINDOWS ---------------------------------------------------------------------------------------------

    private void onWriteClick(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/WriteView.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
