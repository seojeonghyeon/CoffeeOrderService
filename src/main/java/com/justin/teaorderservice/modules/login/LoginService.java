package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     *
     * @param email email
     * @param password 비밀번호
     * @return null 로그인 실패
     */
    public String login(String email, String password) {
        Member findMember = memberRepository.findByEmail(email)
                .filter(member -> passwordEncoder.matches(password, member.getPassword()))
                .orElse(null);
        return findMember != null ? findMember.getId() : null;
    }
}
