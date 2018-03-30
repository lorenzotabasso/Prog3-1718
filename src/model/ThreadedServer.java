package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadedServer implements Runnable{

    private Socket incoming;
    private int counter;
    public String status;

    /**
     Constructs a handler.
     @param in the incoming socket
     @param c the counter for the handlers (used in prompts)
     */
    public ThreadedServer(Socket in, int c) {
        incoming = in;
        counter = c;
        status = "Online";
    }

    public void run() {
        try {
            try {
                // TODO: to rebuild this part! (Maybe put this in openSocket() method)

                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();

                PrintWriter out = new PrintWriter(outStream, true);

                out.println("Server: Hello Client, you're connected to the server");

                System.out.println("Server: Client just connected to the Server");
            }
            finally {
                incoming.close();
            }
        }
        catch (IOException e) {e.printStackTrace();}
    }

    /**
     * It initialize the socket on the Server
     */
    public void openSocket(){

    }
}
