package com.justin.teaorderservice.modules.member;

import com.justin.teaorderservice.modules.authority.Authority;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MemberAdapter extends User {

    @Schema(description = "사용자 정보", nullable = false)
    private final Member member;

    public MemberAdapter(Member member) {
        super(member.getPhoneNumber(), member.getPassword(), authorities(member.getAuthorities()));
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    private static List<GrantedAuthority> authorities(Set<Authority> authorities){
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
                .collect(Collectors.toList());
    }
}
