package com.justin.teaorderservice.modules.exception;


import java.util.Map;

public class ComplexException extends Exception{

    private Map<String, String> errors;

    public ComplexException(Map<String, String> errors){
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
