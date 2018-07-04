package server.model;


import protocol.*;

import java.io.*;
import java.net.Socket;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 */

public class RequestHandler implements Runnable {

    private Socket incoming;
    private int counter;
    private String status;

    //per i file serializable
    private InputStream inStream;
    private OutputStream outStream;


    private final String inboxPath = "/data/emails/inbox/";
    private final String outboxPath = "/data/emails/outbox/";
    private final String draftsPath = "/data/emails/draft/";
    private final String binPath = "/data/emails/bin/";

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private boolean stopRequest = false;


    /**
     * Constructs a handler.
     *
     * @param in the incoming socket
     * @param c  the counter for the handlers (used in prompts)
     */

    RequestHandler(Socket in, int c) {
        incoming = in;
        counter = c;
        status = "Online";
    }


    public void run() {

        try {
            try {

                log("#gestisco client " + incoming.getInetAddress().getHostName());

                while (!stopRequest) { //togliere la negazione

                    Request request = (Request) in.readObject();
                    log("(request) " + request.toString());

                    Response response = manageRequest(request);
                    assert response != null;
                    log("(response) " + response.toString());
                    out.writeObject(response);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                incoming.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Response manageRequest(Request request) {

        Response response = new Response();

        if (request == null)
            return null;

        //faccimao caso che chieda solo lo stato del server
        if (request.getCommand().startsWith("GET")) {
            response = requestGET();
        } else if (request.getCommand().startsWith("AUTH"))
            response = authRequest();
        else if (request.getCommand().startsWith("SEND"))
            response = sendRequest();
        else if (request.getCommand().startsWith("DELETE")) {

            //fare split per capure in che path si trova
            response = deleteRequest();

        } else if (request.getCommand().startsWith("EXIT")) { //exit
            response = exitRequest();
        } else {
            response.setStatus("-1");
            response.setMessage("Invalid request");
        }

        return response;
    }

    private Response exitRequest() {

        stopRequest = true;
        return writeResponse("200", "Bye bye");
    }

    //invio dell'ID dell'email e server elimina
    private Response deleteRequest(String idEmail, String typeEmail) {

        File file = new File(idEmail + ".txt");

        if (file.delete()) {

            log("File deleted successfully");
            return writeResponse("200", "Email deleted successfully");

        } else {

            log("Failed to delete the file");
            return writeResponse("-1", "Failed to delete the file");
        }

    }




    //aggiungere l'oggetto email
    private Response sendRequest() {


    }

    private Response authRequest() {

    }


    //get delle email
    private Response requestGET() {

        return writeResponse("200", getStatus());
    }


    private Response writeResponse(String status, String msg) {

        return new Response(status, msg);
    }


    public String getStatus() {
        return status;
    }


    private void setStatus(String status) {
        this.status = status;
    }


    private void log(String msg) {
        System.out.println("(ThreadClient #" + counter + ")" + msg);
    }

}
