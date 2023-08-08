package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final MemberRepository memberRepository;

    /**
     *
     * @param phoneNumber 핸드폰 번호
     * @param simpleEncryptedPwd 간편 비밀번호
     * @return null 로그인 실패
     */
    @Override
    public Member simpleLogin(String phoneNumber, String simpleEncryptedPwd) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .filter(member -> member.getSimpleEncryptedPwd().equals(simpleEncryptedPwd))
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
