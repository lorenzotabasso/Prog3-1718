package client.task;

import client.model.Client;
import exception.ClientException;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

// TODO: da finire

public class SyncTask extends AbstractTask{

    public SyncTask(Client clientModel) {
        super(clientModel);
    }

    /**
     * It executes an asynchronous SyncTask which exchanges emails with the server.
     *
     * @throws ClientException
     */
    @Override
    public void startTask() throws ClientException {
        clientModel.setStatusProperty("Sincronizzazione delle email in corso...");

        // autenticate() ...

        // ottiene la lista delle email dal server
        // emailListCommand() ...

        // sincronizza le email del server sul client
        // sarebbe bello contarle, così puoi dire "Hei! ha X email nuove!"

        //protocol.quitCommand();

        //Thread.currentThread().interrupt(); // terminate this thread
    }
}
