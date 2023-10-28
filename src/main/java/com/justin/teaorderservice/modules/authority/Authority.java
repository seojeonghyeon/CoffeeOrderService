package com.justin.teaorderservice.modules.authority;


import com.justin.teaorderservice.modules.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Authority {

    @Id @GeneratedValue
    @Column(name = "authority_id")
    private Long id;

    private String authorityName;

    @ManyToMany
    @JoinTable(
            name = "member_authorities",
            joinColumns = @JoinColumn(name = "authority_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id")
    )
    private Set<Member> members = new HashSet<>();

    public static Authority creatAuthority(String role){
        Authority authority = new Authority();
        authority.setAuthorityName(role);
        return authority;
    }

    public void setMember(Member member){
        this.members.add(member);
        member.getAuthorities().add(this);
    }
}
