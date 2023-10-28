package com.justin.teaorderservice.infra.exception;

public class NoSuchPointException extends RuntimeException {
    private ErrorCode errorCode;
    public NoSuchPointException() {
        super();
    }

    public NoSuchPointException(String message) {
        super(message);
    }

    public NoSuchPointException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public NoSuchPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchPointException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
