package client.task;

import client.model.Client;
import client.model.Email;
import exception.EmailException;

// TODO: da finire

public class SendTask extends AbstractTask{

    private Email toSend;

    public SendTask(Client clientModel, Email toSend) {
        super(clientModel);
        this.toSend = toSend;
    }

    @Override
    public void startTask() throws EmailException {
        clientModel.setStatusProperty("Invio email...");

        // SEQUENZA DA IMPLEMENTARE:

        // autenticate() ...

        // sendCommand() ...

        // quitComand() ...
    }

    // Metodo "Salva in bozze?"
}
