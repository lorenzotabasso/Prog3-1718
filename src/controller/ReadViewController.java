package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class ReadViewController implements Initializable, Observer {

    public ReadViewController() {}

    @FXML
    public VBox root;
    public Label to;
    public Label from;
    public Label subject;
    public Label text;
    public Button reply;
    public Button replyToAll;
    public Button forward;
    public Button delete;

    // INITIALIZING ----------------------------------------------------------------------------------------------------

    /**
     * It initialize all the event handlers of the buttons
     */
    private void initializeButtons() {

        // REPLY
        reply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        // REPLY TO ALL
        replyToAll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        // FORWARD
        forward.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        // DELETE
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                // something ...

                closeTab();
            }
        });
    }

    /**
     * It call all the methods that initialize a category of components
     */
    private void initializeAll() {
        initializeButtons();

    }

    // IMPLEMENTATIONS -------------------------------------------------------------------------------------------------

    /**
     * It initialize all the necessary for the GUI
     *
     * @param location:  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources: The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Implementation of update method in Observer interface
     *
     * @param o:   the observable object.
     * @param arg: (optional) an argument passed to the notifyObservers method.
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    // SUPPORT ---------------------------------------------------------------------------------------------------------

    /**
     * It iterate until it founds the Tab element to be closed.
     * Used by closeTab().
     *
     * @param n the node in which we will start the search
     * @return the final Tab to be closed
     */
    private static TabPane findEnclosingTabPane(Node n) {
        while (n != null && !(n instanceof TabPane)) {
            n = n.getParent();
        }
        return (TabPane) n;
    }

    /**
     * It find the tab to be closed and it close it.
     */
    private void closeTab() {
        TabPane tabPane = findEnclosingTabPane(root);
        tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedItem());
    }

} // end class
