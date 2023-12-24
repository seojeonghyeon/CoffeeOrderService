package me.justin.coffeeorderservice.infra.exception;

public class TooMuchAddPointException extends RuntimeException {
    private ErrorCode errorCode;
    public TooMuchAddPointException() {
        super();
    }

    public TooMuchAddPointException(String message) {
        super(message);
    }

    public TooMuchAddPointException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public TooMuchAddPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooMuchAddPointException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
