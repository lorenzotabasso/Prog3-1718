package client.task;

import client.model.Client;
import common.protocol.Request;
import common.protocol.Response;
import exception.ClientException;
import exception.ProtocolException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class AuthTask extends AbstractTask {

    public AuthTask(Client clientModel) {
        super(clientModel);
    }

    /**
     * It executes an asynchronous AuthTask which exchanges emails with the server. It's killed when the iteration with
     * the server has finished.
     *
     * @throws ClientException
     */
    @Override
    public void startTask() throws ClientException {

//        if (!clientModel.serverIsOnline()) {
//            try {
//                clientModel.setSocket(new Socket(clientModel.getServerAddress(), clientModel.getServerPort()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        else {
//            try {
//                clientModel.initializeSocket();
//            } catch (ProtocolException e) {
//                e.printStackTrace();
//            }
//        }

        clientModel.setStatusProperty("Autenticazione in corso...");

        Request rts = new Request("AUTH");
        rts.setAuthor(clientModel.getUser().getUserEmail());

        Response rsp = sendRequest(rts);

        if (manageResponse(rsp)) {
            clientModel.setStatusProperty(clientModel.getUser().getName() + " autenticato!");
        }
        else {
            clientModel.setStatusProperty("Utente inesistente sul server!");
        }

        Thread.currentThread().interrupt(); // terminate this thread

    }
}
