package client.task;

import client.model.Client;
import common.protocol.Request;
import common.protocol.Response;
import exception.ClientException;

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
