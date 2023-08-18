package com.justin.teaorderservice.modules.member;

import com.justin.teaorderservice.infra.exception.ComplexException;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.modules.login.request.RequestLogin;
import com.justin.teaorderservice.modules.member.request.RequestMemberSave;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Tag(
        name = "Member API Controller V1",
        description = "Member API Controller : V1"
)
@Slf4j
@Controller
@RequestMapping("/api/order/v1/members")
@RequiredArgsConstructor
public class MemberApiControllerV1 {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Operation(summary = "회원 가입", description = "Member 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "회원 가입 실패", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/add")
    public ResponseEntity<String> addMember(final @RequestBody @Validated RequestMemberSave requestMemberSave) throws ComplexException {
        Map<String, String> errors = new HashMap<>();

        Authority authority = Authority.builder()
                .authorityName("USER")
                .build();

        Member member = Member.builder()
                .userId(UUID.randomUUID().toString())
                .encryptedPwd(passwordEncoder.encode(requestMemberSave.getPassword()))
                .simpleEncryptedPwd(passwordEncoder.encode(requestMemberSave.getSimplePassword()))
                .phoneNumber(requestMemberSave.getPhoneNumber())
                .createDate(LocalDateTime.now())
                .disabled(false)
                .point(Integer.valueOf(0))
                .authorities(Collections.singleton(authority))
                .build();

        if(memberService.hasPhoneNumber(member.getPhoneNumber())){
            errors.put(
                    requestMemberSave.getPhoneNumber(),
                    String.format(
                            ErrorCode.ExistPhoneNumber.getDescription(),
                            requestMemberSave.getPhoneNumber()
                    )
            );
        }

        if(!errors.isEmpty()){
            throw new ComplexException(errors);
        }

        Member saveMember = memberService.save(member);
        return ResponseEntity.status(HttpStatus.OK).body(saveMember.getUserId());
    }

    @Operation(summary = "회원 가입 여부 확인", description = "사용자의 ID에 대해 등록된 핸드폰 번호가 있는 지 확인 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping("/detail")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<String> memberDetail(@AuthenticationPrincipal MemberAdapter memberAdapter) throws ComplexException{
        Map<String, String> errors = new HashMap<>();
        Member member = memberAdapter.getMember();
        log.info("get: member={}", member);
        String phoneNumber = null;
        if(member == null){
            errors.put(
                    member.getPhoneNumber(),
                    String.format(
                            ErrorCode.NoExistPhoneNumber.getDescription()
                    )
            );
        }else{
            phoneNumber = member.getPhoneNumber();
        }
        
        if(!errors.isEmpty()){
            throw new ComplexException(errors);
        }
        return ResponseEntity.status(HttpStatus.OK).body(phoneNumber);
    }

}
