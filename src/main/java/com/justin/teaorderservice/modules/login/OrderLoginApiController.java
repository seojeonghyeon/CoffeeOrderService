package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.infra.auth.JwtTokenProvider;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.modules.login.request.RequestLogin;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Order Login API Controller V1",
        description = "Order Login API Controller : V1"
)
@Slf4j
@RestController
@RequestMapping("/api/order/v1/login")
@RequiredArgsConstructor
public class OrderLoginApiController {

    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<String> login(final @RequestBody @Validated RequestLogin requestLogin) {

        String memberId = loginService.login(requestLogin.getEmail(), requestLogin.getPassword());

        if(memberId == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorCode.LOGIN_FAIL.getDescription());
        }

        String token = jwtTokenProvider.createToken(memberId);
        log.info(token);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
