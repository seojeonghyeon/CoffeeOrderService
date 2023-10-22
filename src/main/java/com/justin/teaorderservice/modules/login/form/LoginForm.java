package com.justin.teaorderservice.modules.login.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginForm {

    @NotEmpty
    private String email;

    @NotEmpty
    private String simplePassword;
}
