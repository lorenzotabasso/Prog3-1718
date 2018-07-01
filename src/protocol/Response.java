package protocol;

import exception.ProtocolException;

public class Response {
    private String status;
    private String message;

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(String status) {
        this.status = status;
        this.message = null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static Response parseResponse(String res) throws ProtocolException {
        if (res != null) {
            if (Pattern.matches("^(\\+OK|\\-ERR)\\s(\\s|.){1,254}+$", strResponse)) {
                String[] splitted = strResponse.trim().split(" ", 2);
                return new Response(splitted[0].trim(), splitted[1].trim());
            }
        }

        throw new EmailException("Invalid response " + strResponse, EmailException.BAD_DATAGRAM_ERROR);
    }
}
