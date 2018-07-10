package client.task;

import client.model.Client;
import common.Email;
import common.protocol.Request;
import common.protocol.Response;
import exception.ClientException;

public class DeleteTask extends AbstractTask{

    Email toDelete;

    public DeleteTask(Client clientModel, Email toDelete) {
        super(clientModel);
        this.toDelete = toDelete;
    }

    /**
     * It executes an asynchronous DeleteTask which exchanges emails with the server.
     *
     * @throws ClientException
     */
    @Override
    public void startTask() throws ClientException {
        clientModel.setStatusProperty("Eliminazione dell'email in corso...");

        Request rts = new Request("DELETE");
        rts.setParameters(toDelete.getIdEmail());
        rts.setAuthor(clientModel.getUser().getName());

        Response rsp = sendRequest(rts);

        if (manageResponse(rsp)) {
            clientModel.setStatusProperty("Email eliminata con successo!");
        }
        else {
            clientModel.setStatusProperty("Errore nell'eliminazione dell'email!");
        }

    }
}
