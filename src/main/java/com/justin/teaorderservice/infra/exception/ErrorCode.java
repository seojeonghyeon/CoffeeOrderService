package com.justin.teaorderservice.infra.exception;


public enum ErrorCode {
    NO_QUANTITY(4001, false, "No Enable Order Quantity"),
    LESS_QUANTITY_THAN_ORDER_QUANTITY(4002, false,"Less Quantity %d than Order Quantity %d"),
    EXIST_PHONE_NUMBER(4003,false, "Already the phone number is exist : %s"),
    NO_EXIST_PHONE_NUMBER(4004, false, "No exist phone number"),
    LOGIN_FAIL(4005, false, "Login Fail : No exist account"),
    IS_DISABLED_ID(4006, false, "Login Fail : ID is Disabled"),
    NO_MATCH_ORDER_ID_WITH_USER_ID(4007, false, "Order ID isn't in User ID"),
    NO_MATCH_USER_ID(4008, false, "User ID isn't match"),
    NO_TEA(4009, false, "No Tea"),
    NOT_ENOUGH_POINT(4010, false, "Not enough point"),
    NOT_ENOUGH_STOCK(4011, false, "Not enough stock"),
    ALREADY_COMPLETED_ORDER(4012, false, "Already completed order"),
    ALREADY_NOT_PENDING_ORDER(4013, false, "Already not pending order"),
    EXIST_EMAIL(4014,false, "Already the email is exist : %s"),
    NO_EXIST_EMAIL(4015, false, "No exist email");

    private final int code;
    private final Boolean disabled;
    private String description;

    ErrorCode(int code, Boolean disabled, String description){
        this.code = code;
        this.disabled = disabled;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public String getDescription() {
        return description;
    }

}
