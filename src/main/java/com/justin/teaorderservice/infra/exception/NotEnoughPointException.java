package com.justin.teaorderservice.infra.exception;

import lombok.Getter;

public class NotEnoughPointException extends RuntimeException{

    private ErrorCode errorCode;
    public NotEnoughPointException() {
        super();
    }

    public NotEnoughPointException(String message) {
        super(message);
    }

    public NotEnoughPointException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public NotEnoughPointException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughPointException(Throwable cause) {
        super(cause);
    }

    public ErrorCode getErrorCode(){
        return this.errorCode;
    }
}
