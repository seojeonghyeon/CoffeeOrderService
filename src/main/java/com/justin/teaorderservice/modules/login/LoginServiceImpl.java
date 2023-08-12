package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     *
     * @param phoneNumber 핸드폰 번호
     * @param simplePassword 간편 비밀번호
     * @return null 로그인 실패
     */
    @Override
    public Member simpleLogin(String phoneNumber, String simplePassword) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .filter(member -> passwordEncoder.matches(simplePassword, member.getSimpleEncryptedPwd()))
                .orElse(null);
    }

    /**
     *
     * @param phoneNumber 핸드폰 번호
     * @param encryptedPwd 비밀번호
     * @return null 로그인 실패
     */
    @Override
    public Member login(String phoneNumber, String encryptedPwd) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .filter(member -> member.getEncryptedPwd().equals(encryptedPwd))
                .orElse(null);
    }
}
