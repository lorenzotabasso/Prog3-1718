package client;

import client.controller.MainViewController;
import client.model.Client;
import client.task.ExitTask;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class ClientImpl2 extends Application{

    private Client clientModel;
    private ExecutorService exec;

    // DEFAULT METHODS OF JAVAFX ---------------------------------------------------------------------------------------
    @Override
    public void start(Stage primaryStage) throws IOException {

        // Initialize client.controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/view/MainView.fxml"));
        Parent root = fxmlLoader.load();
        MainViewController mainViewController = fxmlLoader.getController();

        // Create emails client.model
        clientModel = new Client("youssef","Mouaddine", "youssef.Mouaddine@unito.it",
                "127.0.0.1", 9000);

        // Create Thread Pool
        exec = Executors.newSingleThreadExecutor();

        // Start client.controller computation
        mainViewController.init(exec, clientModel);

        // Initialize the Scene (Window)
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mailbox di " + clientModel.getUser().getName());
        primaryStage.show();

        // it starts the EXIT Request before closing
        primaryStage.setOnCloseRequest((WindowEvent e) -> {
            exec.submit(new ExitTask(clientModel));
            exec.shutdown();
        });


    }

    /**
     * Access point of ClientImpl
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
