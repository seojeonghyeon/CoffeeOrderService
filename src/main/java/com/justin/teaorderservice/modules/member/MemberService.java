package com.justin.teaorderservice.modules.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;

    public Member findByMemberId(Long memberId) {
        return memberRepository.findById(memberId).filter(member -> !member.getDisabled()).orElse(null);
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElse(null);
    }

    public boolean hasEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public UserDetails loadUserByUsername(String email) {
        Member findMember = memberRepository.findByEmail(email)
                .filter(member -> !member.getDisabled())
                .orElseThrow(() -> new UsernameNotFoundException(format("Not found in Database : {}", email)));
        return new MemberAdapter(findMember);
    }
}
