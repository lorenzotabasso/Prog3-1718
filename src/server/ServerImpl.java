package server;

import server.model.RequestHandler;
import server.model.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
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
