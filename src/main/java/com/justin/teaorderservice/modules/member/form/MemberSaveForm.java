package com.justin.teaorderservice.modules.member.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveForm {

    @Pattern(regexp = "[0-9]{10,11}", message = "10~11자리의 숫자만 입력 가능 합니다")
    @NotEmpty
    private String phoneNumber;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자 입력 가능합니다")
    @NotEmpty
    private String password;

    @Pattern(regexp = "[0-9]{4,5}", message = "간편 비밀번호는 4~5자 숫자만 입력 가능합니다")
    @NotEmpty
    private String simplePassword;

}
