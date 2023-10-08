package com.justin.teaorderservice.infra.exception;

public class AlreadyNotPendingOrderException extends RuntimeException{
    private ErrorCode errorCode;
    public AlreadyNotPendingOrderException() {
        super();
    }

    public AlreadyNotPendingOrderException(String message) {
        super(message);
    }

    public AlreadyNotPendingOrderException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public AlreadyNotPendingOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyNotPendingOrderException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
}
