package com.justin.teaorderservice.infra.exception;

public class AlreadyCompletedPointException extends RuntimeException {
    private ErrorCode errorCode;
    public AlreadyCompletedPointException() {
        super();
    }

    public AlreadyCompletedPointException(String message) {
        super(message);
    }

    public AlreadyCompletedPointException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public AlreadyCompletedPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyCompletedPointException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
}
