package me.justin.coffeeorderservice.infra.exception;

public class NotEnoughStockException extends RuntimeException{
    private ErrorCode errorCode;
    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
