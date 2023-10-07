package com.justin.teaorderservice.modules.member;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class Member {
    private Long id;
    private String memberId;
    private String memberName;
    private String email;
    private String password;
    private String simplePassword;
    private Integer point;
    private Boolean disabled;
    private LocalDateTime createDate;
    private Set<Authority> authorities;

    public void setMember(String userId){
        this.memberId = userId;
        this.disabled = false;
    }
}
