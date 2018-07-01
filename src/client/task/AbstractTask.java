package client.task;

import client.model.Client;
import exception.ClientException;
import exception.ProtocolException;
import exception.ServerException;
import protocol.Protocol;



public abstract class AbstractTask implements Runnable{

    protected Client clientModel;
    protected Protocol protocol;

    public AbstractTask(Client clientModel) {
        this.clientModel = clientModel;
        this.protocol = null;
    }

    /**
     * It executes an asynchronous task which exchanges data with the server.
     *
     * @throws ClientException
     * @throws ServerException
     */
    public abstract void startTask() throws ClientException, ServerException;

    @Override
    public void run() {
        protocol = new Protocol();

        try {
            clientModel.setStatusProperty("Connessione al server in corso...");
            protocol.connect(clientModel.getServerAddress(), clientModel.getServerPort());

            startTask();
        } catch (ClientException e) {
            clientModel.setStatusProperty("Impossibile connettersi al server. Errore: " + e.getExtendedErrorCode());
        } catch (ServerException e) {
            clientModel.setStatusProperty("Errore nel Server. Errore: " + e.getExtendedErrorCode());
        } catch (ProtocolException e) {
            clientModel.setStatusProperty("Errore di comunicazione. Errore: " + e.getExtendedErrorCode());
        } finally {
            try {
                protocol.close();
            } catch (ProtocolException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
