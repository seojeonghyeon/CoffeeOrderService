package com.justin.teaorderservice.modules.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member findByMemberId(String memberId) {
        return memberRepository.findById(memberId).filter(member -> !member.getDisabled()).orElse(null);
    }

    public String findMemberNameByMemberId(String memberId){
        return memberRepository.findById(memberId).filter(member -> !member.getDisabled()).orElse(null).getMemberName();
    }

    @Transactional
    public String register(String email, String encryptedPwd, String simpleEncryptedPwd){
        Member member = Member.createUserMember(email, encryptedPwd, simpleEncryptedPwd);
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public boolean hasEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrMemberId) {
        Member member = memberRepository.findByEmail(emailOrMemberId).orElse(null);
        if(member == null){
            member = memberRepository.findById(emailOrMemberId).orElse(null);
        }

        if(member == null){
            throw new UsernameNotFoundException(emailOrMemberId);
        }
        return new MemberAdapter(member);
    }
}
