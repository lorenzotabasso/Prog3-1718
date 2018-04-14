import model.ThreadedServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerImpl {

    public static void main(String[] args) {
        try {
            int i = 1;
            ServerSocket s = new ServerSocket(9000);

            while (true) {
                Socket incoming = s.accept(); // si mette in attesa di richiesta di connessione e la apre
                System.out.println("Client connected:" + i);
                Runnable r = new ThreadedServer(incoming, i);
                new Thread(r).start();
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
    }

}
