package protocol;

import exception.ProtocolException;

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

    public void connect(String serverAddress, int serverPort) throws ProtocolException {
        try {
            socket = new Socket(serverAddress, serverPort);

            try {
                output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();

                input = new ObjectInputStream(socket.getInputStream());

                // TODO: Response() ...

            } catch (EOFException e) {
                throw new ProtocolException("Connection closed by server", ProtocolException.CONNECTION_CLOSED_BY_SERVER);
            } //catch (ClassNotFoundException e) {
                //throw new ProtocolException(e.getMessage(), ProtocolException.BAD_DATAGRAM_ERROR);
                // TODO: capire la vera implementazione da fare
            //}
        } catch (IOException e) {
            throw new ProtocolException(e.getMessage(), ProtocolException.CONNECTION_ERROR);
        }
    }

    public void close() throws ProtocolException {
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
                throw new ProtocolException(e.getMessage(), ProtocolException.CONNECTION_ERROR);
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
