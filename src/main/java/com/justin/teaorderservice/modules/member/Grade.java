package com.justin.teaorderservice.modules.member;

public enum Grade {
    User(1,"User"),
    Manager(2, "Manager"),
    Admin(3, "Admin");

    private final int code;
    private final String role;

    Grade(int code, String role){
        this.code = code;
        this.role = role;
    }

    public int getCode() {
        return code;
    }

    public String getRole() {
        return role;
    }
}
