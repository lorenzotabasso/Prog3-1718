package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

public class WriteViewController implements Initializable{

    public WriteViewController() {

    }

    @FXML
    public VBox root;
    public GridPane table;
    public TextArea textContainer;
    public ToolBar toolbar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
