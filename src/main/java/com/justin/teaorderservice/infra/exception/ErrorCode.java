package com.justin.teaorderservice.infra.exception;


public enum ErrorCode {
    NoQuantity(4001,"No Enable Order Quantity"),
    LessQuantityThanOrderQuantity(4002,"Less Quantity %d than Order Quantity %d"),
    ExistPhoneNumber(4003,"Already the phone number is exist : %s");

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
