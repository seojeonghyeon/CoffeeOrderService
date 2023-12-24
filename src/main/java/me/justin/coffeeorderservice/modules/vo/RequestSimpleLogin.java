package me.justin.coffeeorderservice.modules.vo;

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
public class RequestSimpleLogin {
    @Schema(description = "사용자 Email", nullable = false, example = "justin@gmail.com")
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$", message = "Email만 입력 가능 합니다")
    @NotEmpty
    private String email;

    @Schema(description = "사용자 간편 비밀번호", nullable = false, example = "1234")
    @Pattern(regexp = "[0-9]{4,5}", message = "간편 비밀번호는 4~5자 숫자만 입력 가능합니다")
    @NotEmpty
    private String simplePassword;
}
