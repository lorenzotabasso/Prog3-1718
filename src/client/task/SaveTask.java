package client.task;

import client.model.Client;
import common.Email;
import common.protocol.Request;
import common.protocol.Response;
import exception.ClientException;

public class SaveTask extends AbstractTask {

    private Email toSave;

    public SaveTask(Client clientModel, Email toSave) {
        super(clientModel);
        this.toSave = toSave;
    }

    /**
     * It executes an asynchronous SendTask which exchanges emails with the server.
     *
     * @throws ClientException
     */
    @Override
    public void startTask() throws ClientException {
        clientModel.setStatusProperty("Invio bozza...");

        Request rts = new Request("SAVE");
        rts.setParameters(toSave);
        rts.setAuthor(clientModel.getUser().getName());

        Response rsp = sendRequest(rts);

        if (manageResponse(rsp)) {
            clientModel.setStatusProperty("Bozza inviata!");
        }
        else {
            clientModel.setStatusProperty("Errore nel server, riprovare!");
        }

        Thread.currentThread().interrupt(); // terminate this thread

    }
}
