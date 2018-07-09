package server.model;

import common.protocol.Request;
import common.protocol.Response;
import common.Email;

import java.io.*;
import java.net.Socket;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class RequestHandler implements Runnable {

    private Socket incoming;
    private String status;
    private int counter;

    private boolean stopRequest = false;
    private final String emailPath = "src/emails/emails/";

    /**
     * Constructs a handler.
     *
     * @param in the incoming socket
     * @param c  the counter for the handlers (used in prompts)
     */

    RequestHandler(Socket in, int c) {

        this.incoming = in;
        this.counter = c;
        this.setStatus("Online");
    }


    public void run() {

        try {
            try {

                log("#gestisco client " + incoming.getInetAddress().getHostName());


                ObjectInputStream in = new ObjectInputStream(incoming.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(incoming.getOutputStream());

                while (!stopRequest) {

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


    /**
     * Manage all the possibility request from the Client.
     *
     * @param request : it is the request object.
     * @return the object Response.
     **/

    private Response manageRequest(Request request) {

        Response response;

        if (request == null)
            return null;//gestire meglio


        if (request.getCommand().startsWith("GET"))
            response = getStatusRequest();

        else if (request.getCommand().startsWith("AUTH"))
            response = authenticationRequest(request.getAuthor());

        else if (request.getCommand().startsWith("SEND"))
            response = sendEmailRequest((Email) request.getParameters());

            //facciamo caso che si può salvare solo la email in draft !
        else if (request.getCommand().startsWith("SAVE"))
            response = saveEmailRequest((Email) request.getParameters());

        else if (request.getCommand().startsWith("DELETE")) {
            //[0] -> id email || [1] -> type email
            String[] parts = request.getParameters().toString().split(" ");
            response = deleteRequest(parts[0], parts[1], request.getAuthor());

        } else if (request.getCommand().startsWith("EXIT"))
            response = exitRequest();

        else
            response = writeResponse(-1, "Invalid request");

        return response;
    }


    //__________________________________________________________________________________________________________________


    /**
     * It allow to save the email in the draft directory.
     * The function save a Serializable object (Email)
     *
     * @param email : object email to have save in the draft
     * @return response : if the email it saved successfully return response with status 200 + message
     * otherwise response with status -1 + message
     **/

    private Response saveEmailRequest(Email email) {

        boolean writed;
        String path;

        //il client dovrebbe fare prima tutti i controlli
        if (email == null)
            return writeResponse(-1, "Invalid email");


        path = getLocation("draft", email.getIdEmail(), email.getSender());
        writed = writeEmail(email, path);


        if (writed)
            return writeResponse(200, "Email saved successfully in draft directory");
        else
            return writeResponse(-1, "Email not saved");

    }

    /**
     * Allow the client to close the communication with the Server
     *
     * @return response with status 200 + message.
     **/

    private Response exitRequest() {

        stopRequest = true;
        setStatus("Offline");
        return writeResponse(200, "Bye bye");
    }


    /**
     * The Client it requires to delete an Email.
     *
     * @param idEmail   : id of the Email
     * @param typeEmail : the type of the email (inbox, outbox, draft)
     * @param user      : the user who do the request
     * @return response  with status 200 + message if the delete operation is confirmed
     * otherwise response  with status -1 + message
     **/

    private Response deleteRequest(String idEmail, String typeEmail, String user) {

        File file = new File(getLocation(typeEmail, idEmail, user));

        if (file.delete()) {

            log("File deleted successfully");
            return writeResponse(200, "Email deleted successfully");

        } else {

            log("Failed to delete the file");
            return writeResponse(-1, "Failed to delete the file");
        }

    }


    /**
     * The Client send the email to another Client.
     * The function write the email in their respective directory.
     *
     * @param email : object Email with all the parameters
     * @return response  with status 200 + message, if it sent successfully
     * otherwise response  with status -1 + message
     **/

    private Response sendEmailRequest(Email email) {

        //boolean writed;
        String path;

        //il client dovrebbe fare prima tutti i controlli
        if (email == null)
            return writeResponse(-1, "Invalid email");


        //Scrivo nella casella di posta inviata
        path = getLocation("outbox", email.getIdEmail(), email.getSender());
        writeEmail(email, path);

        //ora scrivo nella casella di tutti i destinatari dell'email
        for (String receiver : email.getReceiver()) {

            if (search(receiver)) {
                path = getLocation("inbox", email.getIdEmail(), receiver);
                writeEmail(email, path);

            } else {
                //non esiste il destinatario da gestire meglio !
                return writeResponse(-1, "Invalid receiver " + receiver);
            }
        }

        return writeResponse(200, "Email sent to all receiver");
    }


    /**
     * Request to authentication who will start the communication with the Server
     *
     * @param auth : the name of the user
     * @return response  with status 200 + message, if the user is already registered in to the system
     * otherwise response  with status -1 + message
     */

    private Response authenticationRequest(String auth) {

        boolean exist;

        exist = search(auth);

        return ((exist)
                ? writeResponse(200, "Welcome " + auth)
                : writeResponse(-1, "The user " + auth + " don't exist"));

    }

    /**
     * Request to get the status of the Server
     *
     * @return with status 200 + message
     * otherwise response  with status -1 + message
     **/

    private Response getStatusRequest() {

        String msg = getStatus();
        int status = (msg.equals("Online") ? 200 : -1);

        return writeResponse(status, msg);
    }


    //__________________________________________________________________________________________________________________


    /**
     * Getter for the status parameter
     *
     * @return the status of the server
     */

    private String getStatus() {
        return status;
    }

    /**
     * Setter of the status parameter
     *
     * @param status : the new status
     */

    private void setStatus(String status) {
        this.status = status;
    }


    //__________________________________________________________________________________________________________________


    //Tools

    /**
     * Write the object Email in the specific path.
     *
     * @param email : object Email
     * @param path  : the path where need to save the Email
     * @return boolean , true if it written successfully otherwise false
     */

    private synchronized boolean writeEmail(Email email, String path) {

        //gestire l'eccezione
        try {

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(email);

            return true;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }

    /**
     * Create a new Response object.
     *
     * @param status : the status of the response (200, -1)
     * @param msg    : description of the response
     * @return new response with parameter status and msg
     */

    private Response writeResponse(int status, String msg) {

        return new Response(status, msg);
    }


    /**
     * Search if there is the path of the user connect with the Server.
     *
     * @param user : name of the user
     * @return boolean, true if exist otherwise false.
     */

    private boolean search(String user) {

        File file = new File(emailPath);
        String[] directories = file.list();

        if (directories != null) {

            for (String dirUser : directories) {

                if (dirUser.equals(user))
                    return true;
            }
        }
        return false;
    }


    private String getLocation(String typeEmail, String idEmail, String author) {

        return emailPath + "/" + author + "/" + typeEmail + "/" + idEmail + ".txt";
    }


    private void log(String msg) {
        System.out.println("(ThreadClient #" + counter + ")" + msg);
    }

}