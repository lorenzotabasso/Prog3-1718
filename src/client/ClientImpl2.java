package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.IOException;

/**
 *
 * @author lorenzotabasso
 */

public class ClientImpl2 extends Application{

    // DEFAULT METHODS OF JAVAFX ---------------------------------------------------------------------------------------
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("client/view/MainView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Support methods list begin here:
        initializeSocket(9000);
    }

    /**
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

    // TODO: setClientID()

}
