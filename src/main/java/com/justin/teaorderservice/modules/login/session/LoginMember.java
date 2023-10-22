package com.justin.teaorderservice.modules.login.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginMember {
    String memberId;
    public static LoginMember createLoginMember(String memberId){
        LoginMember loginMember = LoginMember.builder()
                .memberId(memberId)
                .build();
        return loginMember;
    }
}
