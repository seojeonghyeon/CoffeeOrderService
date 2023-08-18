package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.modules.member.Member;

public interface LoginService {
    Member simpleLogin(String phoneNumber, String simplePassword);
    Member login(String phoneNumber, String password);
    Member getMemberWithAuthorities(String phoneNumber);
    Member getMembersWithAuthorities();
}
