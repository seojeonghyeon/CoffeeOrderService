package com.justin.teaorderservice.modules.login.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleLoginForm {

    @NotEmpty
    private String phoneNumber;

    @NotEmpty
    private String simplePassword;
}
