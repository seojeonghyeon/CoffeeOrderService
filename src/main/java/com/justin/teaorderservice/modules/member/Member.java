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
    private String userId;
    private String phoneNumber;
    private String password;
    private String simplePassword;
    private Integer point;
    private Boolean disabled;
    private LocalDateTime createDate;
    private Set<Authority> authorities;
<<<<<<< Updated upstream
=======

    public void setUser(String userId){
        this.userId = userId;
        this.point = Integer.valueOf(0);
        this.disabled = false;
    }
>>>>>>> Stashed changes
}
