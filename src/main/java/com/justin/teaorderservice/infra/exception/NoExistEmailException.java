package com.justin.teaorderservice.infra.exception;

public class NoExistEmailException extends RuntimeException{
    private ErrorCode errorCode;
    public NoExistEmailException() {
        super();
    }

    public NoExistEmailException(String message) {
        super(message);
    }

    public NoExistEmailException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public NoExistEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoExistEmailException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
