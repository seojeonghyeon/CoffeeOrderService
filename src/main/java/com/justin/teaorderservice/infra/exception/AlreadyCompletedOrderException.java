package com.justin.teaorderservice.infra.exception;

public class AlreadyCompletedOrderException extends RuntimeException {
    private ErrorCode errorCode;
    public AlreadyCompletedOrderException() {
        super();
    }

    public AlreadyCompletedOrderException(String message) {
        super(message);
    }

    public AlreadyCompletedOrderException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public AlreadyCompletedOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyCompletedOrderException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
}
