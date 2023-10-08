package com.justin.teaorderservice.modules.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justin.teaorderservice.modules.order.Order;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    private LocalDateTime createDate;

    @ManyToMany(mappedBy = "members")
    private Set<Authority> authorities;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    public void setAuthorities(Authority authority){
        this.authorities.add(authority);
        authority.getMembers().add(this);
    }
}
