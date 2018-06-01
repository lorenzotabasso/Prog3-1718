package client.task;

import client.model.Client;
import exception.EmailException;
import protocol.Protocol;



public abstract class AbstractTask implements Runnable{

    protected Client clientModel;
    protected Protocol protocol;

    public AbstractTask(Client clientModel) {
        this.clientModel = clientModel;
        this.protocol = null;
    }

    /**
     * It executes an asynchronous task which exchanges data with the server
     * @throws EmailException
     */
    public abstract void startTask() throws EmailException;

    @Override
    public void run() {
        protocol = new Protocol();

        try {
            clientModel.setStatusProperty("Connessione al server in corso...");
            protocol.connect(clientModel.getServerAddress(), clientModel.getServerPort());

            startTask();
        } catch (EmailException e) {
            switch(e.getErrorCode()){
                case EmailException.CONNECTION_ERROR:
                    clientModel.setStatusProperty("Errore: impossibile connettersi al server");
                    break;

                case EmailException.SERVER_ERROR:
                    clientModel.setStatusProperty("Errore nel Server: " + e.getMessage());
                    break;

                default:
                    break;
            }
        }
        finally {
            try {
                protocol.close();
            } catch (EmailException e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
