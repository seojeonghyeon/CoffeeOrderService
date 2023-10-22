package com.justin.teaorderservice.modules.login.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class RequestLogin {
    @Schema(description = "사용자 Email", nullable = false, example = "justin@gmail.com")
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$", message = "Email만 입력 가능 합니다")
    @NotEmpty
    private String email;

    @Schema(description = "사용자 비밀번호", nullable = false, example = "SEOjh1234!")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자 입력 가능합니다")
    @NotEmpty
    private String password;
}
