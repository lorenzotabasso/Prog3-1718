package exception;

public class ServerException extends Exception{

    public static final int UKNOWN_ERROR = 0;
    public static final int CONNECTION_ERROR = 1;
    public static final int SERVER_ERROR = 2;

    private int errorCode;

    public ServerException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ServerException(String message) {
        this(message, UKNOWN_ERROR);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getExtendedErrorCode() {
        return "SERVER-{" + errorCode + "}";
    }
}
