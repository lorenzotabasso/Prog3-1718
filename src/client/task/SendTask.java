package client.task;

import client.model.Client;
import common.Email;
import common.protocol.Request;
import common.protocol.Response;
import exception.ClientException;

import java.io.IOException;

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
     * It executes an asynchronous SendTask which exchanges data with the server.
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

    public Response sendRequest(Request forServer) {
        Response res = null;

        if (forServer != null) {
            try {
                clientModel.getOutput().writeObject(forServer);

                res = (Response) clientModel.getInput().readObject();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public boolean manageResponse(Response rsp) {
        if (rsp != null) {
            if(rsp.getStatus() == 200) return true;
        }
        return false;
    }
}
