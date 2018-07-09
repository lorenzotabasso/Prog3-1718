package client.task;

import client.model.Client;
import common.protocol.Request;
import common.protocol.Response;
import exception.ClientException;
import exception.ProtocolException;
import exception.ServerException;
import protocol.Protocol;

import java.io.IOException;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public abstract class AbstractTask implements Runnable{

    protected Client clientModel;
    protected Protocol protocol;

    public AbstractTask(Client clientModel) {
        this.clientModel = clientModel;
        this.protocol = null;
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
        protocol = new Protocol();

        try {
            clientModel.setStatusProperty("Connessione al server in corso...");
            protocol.connect(clientModel.getServerAddress(), clientModel.getServerPort());

            startTask();
        } catch (ClientException e) {
            clientModel.setStatusProperty("Impossibile connettersi al server. Errore: " + e.getExtendedErrorCode());
        } catch (ServerException e) {
            clientModel.setStatusProperty("Errore nel Server. Errore: " + e.getExtendedErrorCode());
        } catch (ProtocolException e) {
            clientModel.setStatusProperty("Errore di comunicazione. Errore: " + e.getExtendedErrorCode());
        } finally {
            try {
                protocol.close();
            } catch (ProtocolException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Response sendRequest(Request forServer) {
        Response res = null;

        if (forServer != null) {
            try {
                clientModel.getOutput().writeObject(forServer);

                res = (Response) clientModel.getInput().readObject();

            } catch (IOException e) {
                e.printStackTrace();
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
