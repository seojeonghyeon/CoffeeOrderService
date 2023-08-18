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
public class MemberServiceImpl implements  MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId).filter(member -> member.isDisabled() == false).orElse(null);
    }

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member findByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber).filter(member -> member.isDisabled() == false).orElse(null);
    }

    @Override
    public boolean hasPhoneNumber(String phoneNumber) {
        return findByPhoneNumber(phoneNumber) != null;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) {
        Member findMember = memberRepository.findByUserId(username)
                .filter(member -> member.isDisabled() == false)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Not found in Database : {}", username)));
        return new MemberAdapter(findMember);
    }
}
