package protocol;

import java.io.Serializable;

public class Request implements Serializable {
    private String command;
    private String parameters;

    public Request(String command, String parameters) {
        this.command = command;
        this.parameters = parameters;
    }

    public Request(String command) {
        this.command = command;
        this.parameters = null;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getParameters() {
        return parameters;
    }

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
