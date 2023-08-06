package com.justin.teaorderservice.modules.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements  MemberService{

    private final MemberRepository memberRepository;

    @Override
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member findByPhoneNumber(String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber).orElse(null);
    }

    @Override
    public boolean hasPhoneNumber(String phoneNumber) {
        return findByPhoneNumber(phoneNumber) != null;
    }

}