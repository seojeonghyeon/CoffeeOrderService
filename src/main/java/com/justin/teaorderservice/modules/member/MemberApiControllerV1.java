package com.justin.teaorderservice.modules.member;

import com.justin.teaorderservice.infra.exception.ComplexException;
import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.modules.member.request.RequestMemberSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/api/order/v1/members")
@RequiredArgsConstructor
public class MemberApiControllerV1 {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public ResponseEntity<String> addMember(final @RequestBody @Validated RequestMemberSave requestMemberSave) throws ComplexException {
        Map<String, String> errors = new HashMap<>();

        Member member = Member.builder()
                .userId(UUID.randomUUID().toString())
                .encryptedPwd(passwordEncoder.encode(requestMemberSave.getPassword()))
                .simpleEncryptedPwd(passwordEncoder.encode(requestMemberSave.getSimplePassword()))
                .phoneNumber(requestMemberSave.getPhoneNumber())
                .createDate(LocalDateTime.now())
                .disabled(false)
                .point(Integer.valueOf(0))
                .grade(Grade.User)
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

}
