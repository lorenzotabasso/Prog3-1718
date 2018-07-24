package common.protocol;

import java.io.Serializable;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class Request implements Serializable {


    private String command;
    private Object parameters; // it can be String (get's status) or Email (see SEND comand)
    private String author; // every request has his own author, in order to make the server understand who is sending request to him.


    //todo dovremmo aggiungere l'oggetto Email

    /**
     * Costructor of Response.
     *
     * @param command specify the command that will be send to the server.
     * @param parameters the parameters for the specific command.
     */
    public Request(String command, String parameters) {
        this.command = command;
        this.parameters = parameters;
    }

    /**
     * Overloaded version. Constructor of Response.
     *
     * @param command specify the command that will be send to the server.
     */
    public Request(String command) {
        this.command = command;
        this.parameters = null;
    }

    /**
     * Getter of command parameter.
     *
     * @return the command carried by the Request object.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Setter of command parameter.
     *
     * @param command the new command to be set.
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Getter for parameters.
     *
     * @return the parameters carried by the Request object.
     */
    public Object getParameters() {
        return parameters;
    }

    public String getAuthor() {
        return author;
    }

    /**
     * Setter of command parameter.
     *
     * @param parameters the new parameters to be set.
     */
    public void setParameters(Object parameters) {
        this.parameters = parameters;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", parameters='" + parameters + '\'' +
                ", auth='" + author + '\'' +
                '}';
    }


}
