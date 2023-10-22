package com.justin.teaorderservice.modules.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justin.teaorderservice.modules.authority.Authority;
import com.justin.teaorderservice.modules.member.word.AdjectiveWord;
import com.justin.teaorderservice.modules.member.word.AnimalWord;
import com.justin.teaorderservice.modules.order.Order;
import com.justin.teaorderservice.modules.point.Point;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity @Table(name = "members")
@Getter @Builder @Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member implements Persistable<String> {
    @Id @Column(name = "member_id")
    private String id;
    private String memberName;
    @Column(unique = true)
    private String email;
    private String password;
    private String simplePassword; //with Refresh Token
    private Integer point;
    private Boolean disabled;
    @CreationTimestamp
    private ZonedDateTime createdDate;
    @UpdateTimestamp
    private ZonedDateTime updatedDate;

    @ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
    private Set<Authority> authorities= new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Point> points;

    public void setAuthorities(Authority authority){
        this.authorities.add(authority);
        authority.getMembers().add(this);
    }

    public static Member createUserMember(String email, String encryptedPwd, String simpleEncryptedPwd){
        Authority authority = Authority.creatAuthority("USER");

        Member member = new Member();
        member.setId(UUID.randomUUID().toString());
        member.setEmail(email);
        member.setMemberName(AdjectiveWord.getWordOne()+" "+ AnimalWord.getWordOne());
        member.setPassword(encryptedPwd);
        member.setSimplePassword(simpleEncryptedPwd);
        member.setPoint(0);
        member.setAuthorities(authority);
        member.setDisabled(false);

        authority.setMember(member);
        return member;
    }

    public static Member createManagerMember(String email, String encryptedPwd, String simpleEncryptedPwd){
        Authority authority = Authority.creatAuthority("MANAGER");

        Member member = new Member();
        member.setId(UUID.randomUUID().toString());
        member.setEmail(email);
        member.setMemberName(AdjectiveWord.getWordOne()+" "+ AnimalWord.getWordOne());
        member.setPassword(encryptedPwd);
        member.setSimplePassword(simpleEncryptedPwd);
        member.setPoint(0);
        member.setAuthorities(authority);
        member.setDisabled(false);

        authority.setMember(member);
        return member;
    }

    public static Member createAdminMember(String email, String encryptedPwd, String simpleEncryptedPwd){
        Authority authority = Authority.creatAuthority("ADMIN");

        Member member = new Member();
        member.setId(UUID.randomUUID().toString());
        member.setEmail(email);
        member.setMemberName(AdjectiveWord.getWordOne()+" "+ AnimalWord.getWordOne());
        member.setPassword(encryptedPwd);
        member.setSimplePassword(simpleEncryptedPwd);
        member.setPoint(0);
        member.setAuthorities(authority);
        member.setDisabled(false);

        authority.setMember(member);
        return member;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return getCreatedDate() == null;
    }

    public Integer inducePoint(Integer point){
        this.point += point;
        return this.point;
    }

    public void deductPoint(Integer point){
        this.point -= point;
    }
}
