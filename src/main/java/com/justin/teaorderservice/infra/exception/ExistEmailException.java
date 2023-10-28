package com.justin.teaorderservice.infra.exception;

public class ExistEmailException extends RuntimeException{
    private ErrorCode errorCode;
    public ExistEmailException() {
        super();
    }

    public ExistEmailException(String message) {
        super(message);
    }

    public ExistEmailException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ExistEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistEmailException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
}
