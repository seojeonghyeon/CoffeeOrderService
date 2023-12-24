package me.justin.coffeeorderservice.infra.exception;

public class AlreadyNotPendingPointException extends RuntimeException {
    private ErrorCode errorCode;
    public AlreadyNotPendingPointException() {
        super();
    }

    public AlreadyNotPendingPointException(String message) {
        super(message);
    }

    public AlreadyNotPendingPointException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public AlreadyNotPendingPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyNotPendingPointException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
