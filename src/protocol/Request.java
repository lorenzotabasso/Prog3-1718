package protocol;

import java.io.Serializable;

public class Request implements Serializable {


    private String command;
    private String parameters;

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
    public String getParameters() {
        return parameters;
    }

    /**
     * Setter of command parameter.
     *
     * @param parameters the new parameters to be set.
     */
    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", parameters='" + parameters + '\'' +
                '}';
    }


}
