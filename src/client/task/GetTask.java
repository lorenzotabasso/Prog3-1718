package client.task;

import client.model.Client;
import common.Email;
import common.protocol.Request;
import common.protocol.Response;
import exception.ClientException;

import java.io.IOException;

public class GetTask extends AbstractTask{

    public GetTask(Client clientModel) {
        super(clientModel);
    }

    /**
     * It executes an asynchronous GetTask which exchanges data with the server.
     *
     * @throws ClientException
     */
    @Override
    public void startTask() throws ClientException {
        clientModel.setStatusProperty("Connessione in corso...");

        Request rts = new Request("GET");
        rts.setAuthor(clientModel.getUser().getName());

        Response rsp = sendRequest(rts);

        if (manageResponse(rsp)) {
            clientModel.setStatusProperty("Connesso al server!");
        }
        else {
            clientModel.setStatusProperty("Server non raggiungibile!");
        }

    }
}
