package com.justin.teaorderservice.modules.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;

    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId).filter(member -> member.getDisabled() == false).orElse(null);
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).stream().filter(member -> member.getDisabled;
    }

    public boolean hasEmail(String email) {
        return memberRepository.findByEmail(email) != null;
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Member findMember = memberRepository.findByUserId(username)
                .filter(member -> member.getDisabled() == false)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Not found in Database : {}", username)));
        return new MemberAdapter(findMember);
    }
}
