package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class MainViewController {

    @FXML // MainView components
    public Label status;
    public Label email;
    public Button update;
    public Button write;
    public Button reply;
    public Button replyToAll;
    public Button forward;
    public Button delete;

    @FXML
    public void updateButton(ActionEvent event) {
        System.out.println("You clicked me!");
        status.setText("You clicked: Update");
    }

    @FXML
    public void writeButton(ActionEvent event) {
        System.out.println("You clicked: Write");
        //write.setOnAction(WriteViewController(event));
    }

    @FXML
    public void replyButton(ActionEvent event) {
        System.out.println("You clicked me!");
        status.setText("You clicked: " + status.getId());
    }

    @FXML
    public void replyToAllButton(ActionEvent event) {
        System.out.println("You clicked me!");
        status.setText("You clicked: " + status.getId());
    }

    @FXML
    public void forwardButton(ActionEvent event) {
        System.out.println("You clicked me!");
        status.setText("You clicked: " + status.getId());
    }

    @FXML
    public void deleteButton(ActionEvent event) {
        System.out.println("You clicked me!");
        status.setText("You clicked: " + status.getId());
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
