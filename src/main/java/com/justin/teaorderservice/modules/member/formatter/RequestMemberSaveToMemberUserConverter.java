package com.justin.teaorderservice.modules.member.formatter;

import com.justin.teaorderservice.modules.member.Authority;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.request.RequestMemberSave;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

public class RequestMemberSaveToMemberUserConverter implements Converter<RequestMemberSave, Member> {
    @Override
    public Member convert(RequestMemberSave source) {

        Authority authority = Authority.builder()
                .authorityName("USER")
                .build();

        Member member = Member.builder()
                .userId(UUID.randomUUID().toString())
                .encryptedPwd(source.getPassword())
                .simpleEncryptedPwd(source.getSimplePassword())
                .phoneNumber(source.getPhoneNumber())
                .createDate(LocalDateTime.now())
                .disabled(false)
                .point(Integer.valueOf(0))
                .authorities(Collections.singleton(authority))
                .build();

        return member;
    }
}
