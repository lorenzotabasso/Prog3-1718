package client.task;

import client.model.Client;
import common.Email;
import exception.ClientException;

// TODO: da finire

public class SendTask extends AbstractTask{

    private Email toSend;

    public SendTask(Client clientModel, Email toSend) {
        super(clientModel);
        this.toSend = toSend;
    }

    /**
     * It executes an asynchronous SendTask which exchanges data with the server.
     *
     * @throws ClientException
     */
    @Override
    public void startTask() throws ClientException {
        clientModel.setStatusProperty("Invio email...");

        // SEQUENZA DA IMPLEMENTARE:

        // autenticate() ...

        // sendCommand() ...

        // quitComand() ...
    }

    // Metodo "Salva in bozze?"
}
