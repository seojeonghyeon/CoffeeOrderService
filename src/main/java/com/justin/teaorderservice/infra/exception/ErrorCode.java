package com.justin.teaorderservice.infra.exception;


public enum ErrorCode {
    NoQuantity(5001,"No Enable Order Quantity"),
    LessQuantityThanOrderQuantity(5002,"Less Quantity %d than Order Quantity %d");
    private final int code;
    private final String description;

    ErrorCode(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
