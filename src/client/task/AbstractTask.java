package client.task;

import client.model.Client;
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
     * @throws Exception
     */
    public abstract void startTask();

    @Override
    public void run() {
        protocol = new Protocol();

        try {
            clientModel.setStatusProperty("Connessione al server in corso...");
            protocol.connect(clientModel.getServerAddress(), clientModel.getServerPort());

            startTask();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            protocol.close();
        }
    }
}
