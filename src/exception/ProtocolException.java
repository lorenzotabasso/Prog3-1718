package exception;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class ProtocolException extends Exception {

    public static final int UKNOWN_ERROR = 0;
    public static final int CONNECTION_ERROR = 2;
    public static final int CONNECTION_CLOSED_BY_SERVER = 3;
    public static final int PROTOCOL_ERROR = 4;

    private int errorCode;

    /**
     * Costructor of the exception ProtocolException.
     *
     * @param message the message of the exception.
     * @param errorCode the error code of the exception.
     */
    public ProtocolException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Overloaded version.
     * Costructor of the exception ClientException.
     *
     * @param message the message of the exception.
     */
    public ProtocolException(String message) {
        this(message, UKNOWN_ERROR);
    }

    /**
     * Getter for errorCode parameter.
     *
     * @return the specific error code of the exception
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Pretty-printed getter for errorCode parameter.
     *
     * @return the specific error code of the exception.
     */
    public String getExtendedErrorCode() {
        return "PROTOCOL-{" + errorCode + "}";
    }
}
