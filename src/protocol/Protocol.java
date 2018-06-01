package protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

// TODO: da finire

public class Protocol {

    protected ObjectInputStream input;
    protected ObjectOutputStream output;
    private Socket socket;

    public Protocol() {
        this.input = null;
        this.output = null;
        this.socket = null;
    }

    // COMMON METHODS --------------------------------------------------------------------------------------------------

    public void parseRequest() {

    }

    public void parseResponse() {

    }

    // CLIENT SIDE -----------------------------------------------------------------------------------------------------

    public void autenticate(String username) {
        // TODO: da fare! richiesto dal server per capire chi è l'utente!
    }

    public void connect(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            output = new ObjectOutputStream(socket.getOutputStream());
            output.flush();

            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (socket != null) {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void quitCommand() {
        try {
            output.writeUnshared("QUIT");
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void emailListCommand() {

    }

    public void sendCommand() {

    }

    // SERVER SIDE -----------------------------------------------------------------------------------------------------

    // TODO: da finire

}
