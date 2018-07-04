package protocol;

public class Response {
    private String requestStatus;
    private String message;

    /**
     * Costructor of Response.
     *
     * @param status a string that indicates the status of the server which sends the response.
     * @param message the message to be encapsulated in the Response object.
     *                It's composed by the server for the client
     */
    public Response(String status, String message) {
        this.requestStatus = status;
        this.message = message;
    }

    /**
     * Ovearloded version. Costructor of Response.
     *
     * @param requestStatus a string that indicates the status of the server which sends the response.
     */
    public Response(String requestStatus) {
        this.requestStatus = requestStatus;
        this.message = null;
    }

    /**
     * Getter of status parameter.
     *
     * @return the status carried by the Response object.
     */
    public String getStatus() {
        return requestStatus;
    }

    /**
     * Setter of status parameter.
     * @param status the new status to be set.
     */
    public void setStatus(String status) {
        this.requestStatus = status;
    }

    /**
     * Getter for the message parameter.
     *
     * @return the Response's message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter of message parameter.
     *
     * @param message the new message to be set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + requestStatus + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
