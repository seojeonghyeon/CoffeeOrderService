package com.justin.teaorderservice.modules.member;

public interface MemberService {
    Member save(Member member);
    Member findByPhoneNumber(String phoneNumber);
    boolean hasPhoneNumber(String phoneNumber);
}
