package exception;

public class EmailException extends Exception{

    public static final int UNKNOWN_ERROR = 0;
    public static final int CONNECTION_ERROR = 1;
    public static final int BAD_DATAGRAM_ERROR = 2;
    public static final int PROTOCOL_STATE_ERROR = 3;
    public static final int USER_ERROR = 4;
    public static final int SERVER_ERROR = 5;

    private int errorCode;

    public EmailException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public EmailException(String message) {
        this(message, UNKNOWN_ERROR);
    }

    public int getErrorCode() {
        return errorCode;
    }
}
