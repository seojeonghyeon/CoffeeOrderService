package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     *
     * @param phoneNumber 핸드폰 번호
     * @param simplePassword 간편 비밀번호
     * @return null 로그인 실패
     */
    public Member simpleLogin(String phoneNumber, String simplePassword) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .filter(member -> passwordEncoder.matches(simplePassword, member.getSimplePassword()))
                .orElse(null);
    }

    /**
     *
     * @param phoneNumber 핸드폰 번호
     * @param password 비밀번호
     * @return null 로그인 실패
     */
    public Member login(String phoneNumber, String password) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .filter(member -> passwordEncoder.matches(password, member.getPassword()))
                .orElse(null);
    }
}
