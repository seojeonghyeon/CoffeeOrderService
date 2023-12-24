package me.justin.coffeeorderservice.infra.exception;

public class NoSuchOrderException extends RuntimeException{
    private ErrorCode errorCode;
    public NoSuchOrderException() {
        super();
    }

    public NoSuchOrderException(String message) {
        super(message);
    }

    public NoSuchOrderException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public NoSuchOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchOrderException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
