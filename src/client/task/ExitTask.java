package client.task;

import client.model.Client;
import common.protocol.Request;
import common.protocol.Response;
import exception.ClientException;

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
    }
}
