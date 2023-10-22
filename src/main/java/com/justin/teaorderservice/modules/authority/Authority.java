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

    public static Authority createUserAuthority(){
        Authority authority = Authority.builder()
                .authorityName("USER")
                .build();
        return authority;
    }

    public static Authority creatAuthority(String role){
        Authority authority = Authority.builder()
                .authorityName(role)
                .build();
        return authority;
    }
}
