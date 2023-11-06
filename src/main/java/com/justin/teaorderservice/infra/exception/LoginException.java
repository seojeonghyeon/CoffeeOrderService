package com.justin.teaorderservice.infra.exception;

public class LoginException extends RuntimeException{
    private ErrorCode errorCode;
    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }

    public LoginException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
