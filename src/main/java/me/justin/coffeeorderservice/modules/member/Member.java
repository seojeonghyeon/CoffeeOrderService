package me.justin.coffeeorderservice.modules.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import me.justin.coffeeorderservice.modules.authority.Authority;
import me.justin.coffeeorderservice.modules.order.Order;
import me.justin.coffeeorderservice.modules.point.Point;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import java.time.ZonedDateTime;
import java.util.*;

@Entity @Table(name = "members")
@Getter @Setter(AccessLevel.PROTECTED)
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

    public void setUser(String userId) {
        this.id = userId;
        this.point = Integer.valueOf(0);
        this.disabled = false;
    }

    @ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
    private Set<Authority> authorities= new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Point> points = new ArrayList<>();

    public void setAuthorities(Authority authority){
        this.authorities.add(authority);
        authority.getMembers().add(this);
    }

    public static Member createUserMember(String email, String encryptedPwd, String simpleEncryptedPwd){
        Authority authority = Authority.creatAuthority("USER");
        return createMember(authority, email, encryptedPwd, simpleEncryptedPwd);
    }

    public static Member createManagerMember(String email, String encryptedPwd, String simpleEncryptedPwd){
        Authority authority = Authority.creatAuthority("MANAGER");
        return createMember(authority, email, encryptedPwd, simpleEncryptedPwd);
    }

    public static Member createAdminMember(String email, String encryptedPwd, String simpleEncryptedPwd){
        Authority authority = Authority.creatAuthority("ADMIN");
        return createMember(authority, email, encryptedPwd, simpleEncryptedPwd);
    }

    private static Member createMember(Authority authority, String email, String encryptedPwd, String simpleEncryptedPwd){
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
