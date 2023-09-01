package com.justin.teaorderservice.infra.exception;


public enum ErrorCode {
    NoQuantity(4001,"No Enable Order Quantity"),
    LessQuantityThanOrderQuantity(4002,"Less Quantity %d than Order Quantity %d"),
    ExistPhoneNumber(4003,"Already the phone number is exist : %s"),
    NoExistPhoneNumber(4004, "No exist phone number"),
    LoginFail(4005, "Login Fail : No exist account"),
    IDisDisabled(4006, "Login Fail : ID is Disabled"),
    NoMatchOrderIdWithUserId(4007, "Order ID isn't in User ID"),
    NoMatchUserID(4008, "User ID isn't match"),
    NoTea(4009, "No Tea");

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
