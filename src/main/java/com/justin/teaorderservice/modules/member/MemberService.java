package com.justin.teaorderservice.modules.member;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {
    Member findByUserId(String userId);
    Member save(Member member);
    Member findByPhoneNumber(String phoneNumber);
    boolean hasPhoneNumber(String phoneNumber);
}
