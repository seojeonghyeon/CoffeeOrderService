package com.justin.teaorderservice.modules.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justin.teaorderservice.modules.order.Order;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "members")
@Getter @Setter
@Builder  @NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String memberName; // Adjective + Animal Name

    @Column(unique = true)
    private String email; //for duplicate validation in multi thread environment

    private String password; //General

    private String simplePassword; //with Refresh Token

    private Integer point;

    private Boolean disabled;

    private ZonedDateTime createDate;

    @ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
    private Set<Authority> authorities;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    public void setAuthorities(Authority authority){
        this.authorities.add(authority);
        authority.getMembers().add(this);
    }

    public static Member createUserMember(String email, String password, String simplePassword){
        Set<Authority> authorities = new HashSet<>();
        authorities.add(Authority.createUserAuthority());

        Member member = Member.builder()
                .memberName(AdjectiveWord.getWordOne() + AnimalWord.getWordOne())
                .email(email)
                .password(password)
                .simplePassword(simplePassword)
                .point(0)
                .disabled(false)
                .createDate(ZonedDateTime.now())
                .authorities(authorities)
                .build();
        return member;
    }
}
