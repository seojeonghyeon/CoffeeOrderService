package com.justin.teaorderservice.infra.exception;


import java.util.Map;

public class ComplexException extends Exception {

    private final ResponseError responseError;

    public ComplexException(ResponseError responseError){
        this.responseError = responseError;
    }

    public ResponseError getResponseError() {
        return responseError;
    }
}
