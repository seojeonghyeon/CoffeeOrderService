package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.infra.auth.JwtTokenProvider;
import com.justin.teaorderservice.infra.exception.ComplexException;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.modules.login.request.RequestLogin;
import com.justin.teaorderservice.modules.member.Member;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Tag(
        name = "Order Login API Controller V1",
        description = "Order Login API Controller : V1"
)
@Slf4j
@RestController
@RequestMapping("/api/order/v1/login")
@RequiredArgsConstructor
public class OrderLoginApiControllerV1 {

    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<String> loginV1(final @RequestBody @Validated RequestLogin requestLogin) throws ComplexException {
        Map<String, String> errors = new HashMap<>();

        Member member = loginService.login(
                requestLogin.getPhoneNumber(),
                requestLogin.getPassword()
        );

        if(member == null){
            errors.put(
                    requestLogin.getPhoneNumber(),
                    String.format(
                            ErrorCode.LoginFail.getDescription()
                    )
            );
        }else if(member.isDisabled()){
            errors.put(
                    requestLogin.getPhoneNumber(),
                    String.format(
                            ErrorCode.IDisDisabled.getDescription()
                    )
            );
        }

        if(!errors.isEmpty()){
            throw new ComplexException(errors);
        }
        String token = jwtTokenProvider.createToken(member.getUserId());
        log.info(token);

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
