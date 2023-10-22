package com.justin.teaorderservice.infra.exception;

public class NoSuchMemberException extends RuntimeException {
    private ErrorCode errorCode;
    public NoSuchMemberException() {
        super();
    }

    public NoSuchMemberException(String message) {
        super(message);
    }

    public NoSuchMemberException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public NoSuchMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchMemberException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
