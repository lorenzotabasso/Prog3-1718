package protocol;

import exception.EmailException;

import java.io.EOFException;
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

    public void connect(String serverAddress, int serverPort) throws EmailException{
        try {
            socket = new Socket(serverAddress, serverPort);

            try {
                output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();

                input = new ObjectInputStream(socket.getInputStream());
            } catch (EOFException e) {
                throw new EmailException("Connection closed by server", EmailException.CONNECTION_ERROR);
            } catch (ClassNotFoundException e) { // TODO: capir ela vera implementazione da fare
                throw new EmailException(e.getMessage(), EmailException.BAD_DATAGRAM_ERROR);
            }
        } catch (IOException e) {
            throw new EmailException(e.getMessage(), EmailException.CONNECTION_ERROR);
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws EmailException {
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
