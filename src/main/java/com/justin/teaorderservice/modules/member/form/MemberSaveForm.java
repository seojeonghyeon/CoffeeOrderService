package com.justin.teaorderservice.modules.member.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveForm {

    @NotEmpty
    private String phoneNumber;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

}
