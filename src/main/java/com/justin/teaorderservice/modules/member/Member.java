package com.justin.teaorderservice.modules.member;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Member {
    private Long id;
    private String userId;
    private String phoneNumber;
    private String email;
    private String password;
}
