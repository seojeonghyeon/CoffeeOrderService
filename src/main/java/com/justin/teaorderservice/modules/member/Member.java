package com.justin.teaorderservice.modules.member;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Member {
    private Long id;
    private String userId;
    private String phoneNumber;
    private String encryptedPwd;
    private String simpleEncryptedPwd;
    private Integer point;
    private boolean disabled;
    private LocalDateTime createDate;
    private Grade grade;
}
