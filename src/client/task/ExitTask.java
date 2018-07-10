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

public class ExitTask extends AbstractTask{

    public ExitTask(Client clientModel) {
        super(clientModel);
    }

    /**
     * It executes an asynchronous ExitTask which exchanges emails with the server.
     *
     * @throws ClientException
     */
    @Override
    public void startTask() throws ClientException {
        clientModel.setStatusProperty("Disconnessione in corso...");

        Request rts = new Request("EXIT");
        rts.setAuthor(clientModel.getUser().getName());

        Response rsp = sendRequest(rts);

        // TODO: scrivere nel log che è avvenuta la disconnessione.

        if (manageResponse(rsp)) {
            clientModel.setStatusProperty("Disconnesso.");
        }
        else {
            // anche se non ci arriverà mai...
            clientModel.setStatusProperty("Errori durante la disconnessione col server...");
        }

        // we will not terminate this thread here but instead in ClientImpl
    }
}
