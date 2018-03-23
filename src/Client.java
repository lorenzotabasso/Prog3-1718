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

public class Client extends Application{

    @Override
    public void start(Stage primaryStage) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("view/MainView.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        initializeSocket();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void initializeSocket(){
        try {
            Socket socket = new Socket("127.0.0.1", 9000);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String answer = input.readLine();
            System.out.println(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
