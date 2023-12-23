package com.justin.teaorderservice.modules.member;

import com.justin.teaorderservice.infra.auth.JwtTokenProvider;
import com.justin.teaorderservice.infra.exception.*;
import com.justin.teaorderservice.modules.event.MemberCreatedEvent;
import com.justin.teaorderservice.modules.vo.RequestMemberSave;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.justin.teaorderservice.modules.member.MemberApiController.ROOT;

@Tag(
        name = "Member API Controller",
        description = "Member API Controller"
)
@Slf4j
@Controller
@RequestMapping(ROOT)
@RequiredArgsConstructor
public class MemberApiController {

    static final String ROOT = "/api/order/members";
    static final String ADD_MEMBER = "/add";
    static final String ADD_MEMBER_RESULT = "/{email}/detail";

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ApplicationEventPublisher eventPublisher;

    @Operation(summary = "회원 가입 양식", description = "Member 추가 양식")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 가입 양식 전달", content = @Content(schema = @Schema(implementation = RequestMemberSave.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping(ADD_MEMBER)
    public ResponseEntity<RequestMemberSave> addMember(){
        return ResponseEntity.status(HttpStatus.OK).body(new RequestMemberSave());
    }

    @Operation(summary = "회원 가입", description = "Member 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 가입 성공", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "회원 가입 실패", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(ADD_MEMBER)
    public ResponseEntity<String> addMember(final @RequestBody @Validated RequestMemberSave requestMemberSave) {
        boolean hasAccount = memberService.hasEmail(requestMemberSave.getEmail());
        if(hasAccount){
            throw new ExistEmailException(ErrorCode.EXIST_EMAIL);
        }

        Member saveMember = memberService.register(requestMemberSave.getEmail(), passwordEncoder.encode(requestMemberSave.getPassword()), passwordEncoder.encode(requestMemberSave.getSimplePassword()));
        eventPublisher.publishEvent(new MemberCreatedEvent(saveMember));
        return ResponseEntity.status(HttpStatus.OK).body(jwtTokenProvider.createToken(saveMember.getId()));
    }

    @Operation(summary = "회원 가입 여부 확인", description = "사용자의 ID에 대해 등록된 핸드폰 번호가 있는 지 확인 한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(schema = @Schema(implementation = MemberAdapter.class))),
            @ApiResponse(responseCode = "400", description = "Request Fail", content = @Content(schema = @Schema(implementation = MemberAdapter.class))),
            @ApiResponse(responseCode = "500", description = "Server Error", content = @Content(schema = @Schema(implementation = MemberAdapter.class)))
    })
    @GetMapping(ADD_MEMBER_RESULT)
    public ResponseEntity<String> memberDetail(@PathVariable("email") final String email){
        boolean hasAccount = memberService.hasEmail(email);
        if(!hasAccount){
            throw new NoExistEmailException(ErrorCode.NO_EXIST_EMAIL);
        }
        return ResponseEntity.status(HttpStatus.OK).body(email);
    }



}
