package server.model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 */

public class Server {

    private String status = "offline";
    private ServerSocket server = null;

    private int numClient = 0;
    private boolean isRunnig = false;

    private int port;

    public Server(int port) throws IOException {

        this.port = port;
        this.server = new ServerSocket(this.port, 0, InetAddress.getByName(null));

        setStatus("online");
        log("Online alla porta " + this.port);

    }

    public void init() {

        isRunnig = true;

        //fare condizione di uscita
        while (isRunnig) {

            try {

                log("Numero di cliente connessi " + getNumClient());
                log("Attesa client ... ");

                Socket clientSocket = this.server.accept();

                numClient++;

                log("Client " + clientSocket.getInetAddress() + " connesso con succeso");

                Runnable r = new RequestHandler(clientSocket, numClient);
                Thread t = new Thread(r);
                t.start();



            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                this.setStatus("offline");
            }
        }

        close();
    }


    //metodo client connessi


    // GETTERS ---------------------------------------------------------------------------------------------------------

    public String getStatus() {
        return this.status;
    }


    // SETTERS ---------------------------------------------------------------------------------------------------------

    private void setStatus(String newStatus) {
        this.status = newStatus;
    }


    // UTILITY ---------------------------------------------------------------------------------------------------------


    public int getPort() {
        return this.port;
    }


    private int getNumClient() {
        return this.numClient;
    }


    private void close() {

        try {
            isRunnig = false;
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //funzione per sviluppo
    public void log(String msg) {
        System.out.println("(Server) : " + msg);
    }
}