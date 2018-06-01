package client;

import client.controller.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import client.model.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 */

public class ClientImpl1 extends Application{

    private Client clientModel;
    private ExecutorService exec;

    // DEFAULT METHODS OF JAVAFX ---------------------------------------------------------------------------------------
    @Override
    public void start(Stage primaryStage) throws IOException {

        // Initialize client.controller
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/view/MainView.fxml"));
        Parent root = fxmlLoader.load();
        MainViewController mainViewController = fxmlLoader.getController();

        // Create data client.model
        clientModel = new Client("Lorenzo","Tabasso", "lorenzo.tabasso@unito.it", "127.0.0.1", 9000);

        // Create Thread Pool
        exec = Executors.newSingleThreadExecutor();

        // Start client.controller computation
        mainViewController.init(exec, clientModel);

        // Initialize the Scene (Window)
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Mailbox di " + clientModel.getUser().getName());
        primaryStage.show();

        // Support methods list begin here: // TODO: spostare socket dentro Client e Server
        initializeSocket(9000);
    }

    /**
     * Access point of ClientImpl
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    // SUPPORT METHODS FOR JAVAFX --------------------------------------------------------------------------------------
    // (To add in Start() method!)

    public void initializeSocket(int port){
        try {
            Socket clientSocket = new Socket("localhost", port);
            System.out.println("Client: Ho aperto il socket verso il server");

            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println(input.readLine());
        } catch (IOException e) { e.printStackTrace(); }
    }
}
