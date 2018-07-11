package client.task;

import client.model.Client;
import common.protocol.Request;
import common.protocol.Response;
import exception.ClientException;
import exception.ProtocolException;
import exception.ServerException;
import javafx.scene.control.Alert;


import java.io.IOException;
import java.net.Socket;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public abstract class AbstractTask implements Runnable{

    protected Client clientModel;

    public AbstractTask(Client clientModel) {
        this.clientModel = clientModel;
    }

    /**
     * It executes an asynchronous task which exchanges emails with the server.
     *
     * @throws ClientException
     * @throws ServerException
     */
    public abstract void startTask() throws ClientException, ServerException;

    @Override
    public void run() {

        try {
            clientModel.setStatusProperty("Connessione al server in corso...");
            startTask();

        } catch (ClientException e) {
            clientModel.setStatusProperty("Impossibile connettersi al server. Errore: " + e.getExtendedErrorCode());
        } catch (ServerException e) {
            //clientModel.setStatusProperty("Errore nel Server. Errore: " + e.getExtendedErrorCode());
            System.out.println(e.getMessage());
        }
    }

    public Response sendRequest(Request forServer) throws ClientException{
        Response res = null;

        if (forServer != null) {
            try {
                if (clientModel.serverIsOnline()) {
                    clientModel.getOutput().writeObject(forServer);
                    res = (Response) clientModel.getInput().readObject();
                }
                else
                    throw new ClientException("Server offline", 1);

            } catch (IOException e) {
                throw new ClientException(e.getMessage(), 1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public boolean manageResponse(Response rsp) {
        if (rsp != null) {
            if(rsp.getStatus() == 200) return true;
        }
        return false;
    }

}
