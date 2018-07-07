package protocol;

import common.Email;
import exception.ProtocolException;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

// TODO: da finire (spostare il necessario ed eliminare)

public class Protocol {

    protected ObjectInputStream input;
    protected ObjectOutputStream output;
    private Socket socket;

    /**
     * Costructor for Protocol object.
     */
    public Protocol() {
        this.input = null;
        this.output = null;
        this.socket = null;
    }

    // COMMON METHODS --------------------------------------------------------------------------------------------------

    /**
     * It parse a Request object
     */
    public void parseRequest() {

    }

    /**
     * It parse a Response object
     */
    public void parseResponse() {

    }

    // CLIENT SIDE -----------------------------------------------------------------------------------------------------

    /*
    Passi dell'iterazione:

    1) connect(...): il client apre una connessione verso il server.
    2) autenticateCommand(...): il client chiede al server di autenticarsi.

    3) emailListCommand(): il client chiede al server di inviargli la lista delle email per l'account autenticato.
    4) editFlagCommand(): il client chiede al server di modificare un flag dell'email (serve per il campo "seen" in Email)

    5) sendCommand(): il client chiede al server di poter inviare una nuova email.

    6) quitCommand(): il client invia al server la richiesta di chiudere la connessione
    7) close(): il client chiude la connessione con il server.

    CON YOSS:

    0) connect (automatico, si connette al localhost e USA la get() )
    (1) get) (Prima di ogni richiesta verificare che lo stato sia 200)
    2) auth ok
    3) send ok
    4) delete
    5) exit ok
     */

    /**
     * It opens a connection from the client to the server
     *
     * @param serverAddress the server address
     * @param serverPort the server port
     * @throws ProtocolException
     */
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

    /**
     * It starts the authenticating procedure with the server
     *
     * @param username
     */
    public void authenticateCommand(String username) {
        // TODO: da fare! richiesto dal server per capire chi è l'utente!
    }

    /**
     * It sends to the server the request of the email list for the authenticated account, once that the server
     * sends his reply, it loads the list of the email just received from the server.
     */
    public void emailListCommand() {

    }

    /**
     * it sends to the server the request to edit an email's flag (for example "seen").
     */
    public void editFlagCommand() {

    }

    /**
     * It sends to the server the request to send an email by using the right command.
     * It wait for the server response.
     * It effectively sends the email to the server.
     *
     * @param receivers and Arraylist that contains all the receivers of the email to send.
     * @param toSend the email to send.
     */
    public void sendCommand(ArrayList<String> receivers, Email toSend) {

    }

    /**
     * It sends to the server the command that request the closure of the connection.
     */
    public void quitCommand() {
        try {
            output.writeUnshared("QUIT");
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It closes the connection previously established with the server.
     *
     * @throws ProtocolException
     */
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
        // TODO: in caso che il socket non sia inizializato ? Fare ramo else.
    }


    // SERVER SIDE -----------------------------------------------------------------------------------------------------

    /*
    Passi dell'iterazione:

    1) authenticateProcess(...): il server riceve la richiesta di autenticazione da parte del client.

    3) emailListProcess(): il server riceve dal client la richiesta di invio della lista delle email.
    4) editFlagProcess(): il server riceve dal client la richiesta di modificare un flag (es. "seen") di una email.

    5) sendProcess(): il server riceve la richiesta del client di inviare una email.
    6) effectiveSendProcess(): il server riceve l'email effettiva da inviare all'account del destinatario sul server.

    7) quitProcess(): il server riceve la richiesta dal client di chiudere la connessione.
     */

    /**
     * It handles the authentication command sent from the client.
     */
    public void authenticationProcess() {

    }

    /**
     * It handles the emailList command sent from the client.
     */
    public void emailListProcess() {

    }

    /**
     * It handles the editFlag command sent from the client.
     */
    public void editFlagProcess() {

    }

    /**
     * It handles the send command sent from the client.
     */
    public void sendProcess() {

    }

    /**
     * After handling the sendCommand, the server effectively recives the email to send.
     *
     * @param toSend the email just received to send to the receiver'account on the server.
     */
    public void effectiveSendProcess(common.Email toSend) {

    }

    /**
     * It handles the quit command sent from the client.
     */
    public void quitProcess() {

    }

}
