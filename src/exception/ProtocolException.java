package exception;

public class ProtocolException extends Exception {

    public static final int UKNOWN_ERROR = 0;
    public static final int BAD_DATAGRAM_ERROR = 1;
    public static final int CONNECTION_ERROR = 2;
    public static final int CONNECTION_CLOSED_BY_SERVER = 3;
    public static final int PROTOCOL_ERROR = 4;

    private int errorCode;

    public ProtocolException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ProtocolException(String message) {
        this(message, UKNOWN_ERROR);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getExtendedErrorCode() {
        return "PROTOCOL-{" + errorCode + "}";
    }
}
