package exception;

/**
 * @author Lorenzo Tabasso
 * @author Youssef Mouaddine
 * @author Antonio Guarino
 */

public class ServerException extends Exception{

    public static final int UKNOWN_ERROR = 0;
    public static final int CONNECTION_ERROR = 1;
    public static final int SERVER_ERROR = 2;

    private int errorCode;

    /**
     * Costructor of the exception ServerException.
     *
     * @param message the message of the exception.
     * @param errorCode the error code of the exception.
     */
    public ServerException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Overloaded version.
     * Costructor of the exception ServerException.
     *
     * @param message the message of the exception.
     */
    public ServerException(String message) {
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
        return "SERVER-{" + errorCode + "}";
    }
}
