package client.task;

import client.model.Client;
import common.Email;
import common.protocol.Request;
import common.protocol.Response;
import exception.ClientException;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class SendTask extends AbstractTask{

    private Email toSend;

    public SendTask(Client clientModel, Email toSend) {
        super(clientModel);
        this.toSend = toSend;
    }

    /**
     * It executes an asynchronous SendTask which exchanges emails with the server.
     *
     * @throws ClientException
     */
    @Override
    public void startTask() throws ClientException {
        clientModel.setStatusProperty("Invio email...");

        Request rts = new Request("SEND");
        rts.setParameters(toSend);
        rts.setAuthor(clientModel.getUser().getName());

        Response rsp = sendRequest(rts);

        if (manageResponse(rsp)) {
            clientModel.setStatusProperty("Email inviata!");
        }
        else {
            clientModel.setStatusProperty("Errore nel server, riprovare!");
        }

    }
}
