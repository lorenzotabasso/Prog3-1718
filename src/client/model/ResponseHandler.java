package client.model;

import common.Email;
import common.protocol.Request;
import common.protocol.Response;
import exception.ProtocolException;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ResponseHandler {

    private Client clientModel;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket socket;

    public ResponseHandler(Client thisClient, String serverAddress, int serverPort) {
        try {
            this.clientModel = thisClient;
            this.socket = new Socket(serverAddress, serverPort);

            this.input = new ObjectInputStream(socket.getInputStream());
            this.output = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Comandi: GET, AUT, SEND, DELETE, EXIT

    // Il client non può avere questo metodo, deve attendere
    // la risposta all'interno di ogni metodo che la invia.
    // altrimenti come fa il client a capire a che Request
    // è associata la Response?
    public void manageResponse(Response fromServer) {

    }

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


                // dovrebbero essere nel getComand()
                // TODO: problema: come fa il server a capire chi gli sta mandando la richiesta?
                // nella Request c'è il campo autore, ma nell'autore, dobbiamo mettere il client
                // o l'account dell'utente?
                Request forServer = new Request("GET");
                output.writeObject(forServer);

                Response fromServer = (Response) input.readObject();

                if (fromServer.getStatus() == 200 && fromServer.getMessage() == "Online") {

                    // authenticateCommand(); fa qualcosa

                    this.clientModel.setStatusProperty("Online - connected to the Server");
                }
                else {
                    this.clientModel.setStatusProperty("Offline");
                }

            } catch (EOFException e) {
                throw new ProtocolException("Connection closed by server", ProtocolException.CONNECTION_CLOSED_BY_SERVER);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                //throw new ProtocolException(e.getMessage(), ProtocolException.BAD_DATAGRAM_ERROR);
                // TODO: capire la vera implementazione da fare
            }
        } catch (IOException e) {
            throw new ProtocolException(e.getMessage(), ProtocolException.CONNECTION_ERROR);
        }
    }

    public void getComand() {

    }

    /**
     * It starts the authenticating procedure with the server
     *
     * @param username
     */
    public void authenticateCommand(String username) {
        // TODO: da fare! richiesto dal server per capire chi è l'utente!
    }

    public void sendComand(Email toSend) {

        // Alla fine di tutta l'iterazione, attenderà una response dal server
        clientModel.setStatusProperty("Email inviata");

    }

    public void deleteComand(Email toDelete) {

    }

    public void exitComand() {

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

}
