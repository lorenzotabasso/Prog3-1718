package server;


import server.model.Server;
import java.io.IOException;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class ServerImpl {

    public static void main(String[] args) {
        Server s;

        try {
            s = new Server(9000);
            s.log("Server connesso... \n");
            s.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
