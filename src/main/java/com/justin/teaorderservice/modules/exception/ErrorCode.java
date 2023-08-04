package com.justin.teaorderservice.modules.exception;


public enum ErrorCode {
    NoQuantity(5001,"No Enable Order Quantity"),
    LessQuantityThanOrderQuantity(5002,"Less Quantity %d than Order Quantity %d");
    private int code;
    private String description;

    private ErrorCode(int code, String description){
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
