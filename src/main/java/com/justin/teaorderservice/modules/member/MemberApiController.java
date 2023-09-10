package com.justin.teaorderservice.modules.member;

import com.justin.teaorderservice.infra.exception.ComplexException;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.infra.exception.ResponseError;
import com.justin.teaorderservice.modules.member.request.RequestMemberSave;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
public class MemberApiController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Operation(summary = "회원 가입", description = "Member 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "회원 가입 실패", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/add")
    public ResponseEntity<String> addMember(final @RequestBody @Validated RequestMemberSave requestMemberSave) throws ComplexException {
        requestMemberSave.encodePassword(passwordEncoder.encode(requestMemberSave.getPassword()), passwordEncoder.encode(requestMemberSave.getSimplePassword()));
        Member member = modelMapper.map(requestMemberSave, Member.class);
        member.setUser(UUID.randomUUID().toString());

        if(memberService.hasPhoneNumber(member.getPhoneNumber())){
            ResponseError responseError = ResponseError.builder()
                    .errorCode(ErrorCode.EXIST_PHONE_NUMBER)
                    .target(requestMemberSave.getPhoneNumber())
                    .build();
            throw new ComplexException(responseError);
        }

        Member saveMember = memberService.save(member);
        return ResponseEntity.status(HttpStatus.OK).body(saveMember.getUserId());
    }

    @Operation(summary = "회원 가입 여부 확인", description = "사용자의 ID에 대해 등록된 핸드폰 번호가 있는 지 확인 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = MemberAdapter.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = MemberAdapter.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = MemberAdapter.class)))
    })
    @GetMapping("/detail")
    @PreAuthorize("hasAnyAuthority('USER','MANAGER','ADMIN')")
    public ResponseEntity<String> memberDetail(@AuthenticationPrincipal MemberAdapter memberAdapter) throws ComplexException{
        Member member = memberAdapter.getMember();
        log.info("get: member={}", member); //memberId 정도만 아니면 키값정도만
        String phoneNumber = null;

        if(member == null){
            ResponseError responseError = ResponseError.builder()
                    .errorCode(ErrorCode.NO_EXIST_PHONE_NUMBER)
                    .target(member.getPhoneNumber())
                    .build();
            throw new ComplexException(responseError);
        }else{
            phoneNumber = member.getPhoneNumber();
        }

        return ResponseEntity.status(HttpStatus.OK).body(phoneNumber);
    }

}
