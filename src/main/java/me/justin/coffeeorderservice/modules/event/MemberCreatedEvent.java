package me.justin.coffeeorderservice.modules.event;

import me.justin.coffeeorderservice.modules.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberCreatedEvent {
    private final Member member;
}
