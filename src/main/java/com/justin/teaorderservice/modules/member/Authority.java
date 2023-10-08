package com.justin.teaorderservice.modules.member;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED)
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
}
