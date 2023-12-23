package com.justin.teaorderservice.modules.event;

import com.justin.teaorderservice.modules.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreatedEvent {
    private final Member member;
}
