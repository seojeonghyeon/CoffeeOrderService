package com.justin.teaorderservice.modules.member;


import com.justin.teaorderservice.modules.member.form.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/order/v1/members")
@RequiredArgsConstructor
public class MemberControllerV1 {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/add")
    public String addMember(@ModelAttribute MemberSaveForm memberSaveForm){
        return "members/v1/addMember";
    }

    @PostMapping("/add")
    public String saveMember(@ModelAttribute @Validated MemberSaveForm memberSaveForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "members/v1/addMember";
        }

        Member member = Member.builder()
                .userId(UUID.randomUUID().toString())
                .encryptedPwd(passwordEncoder.encode(memberSaveForm.getPassword()))
                .simpleEncryptedPwd(passwordEncoder.encode(memberSaveForm.getSimplePassword()))
                .phoneNumber(memberSaveForm.getPhoneNumber())
                .createDate(LocalDateTime.now())
                .disabled(false)
                .point(Integer.valueOf(0))
                .grade(Grade.User)
                .build();

        //핸드폰 중복 검증
        //중복 시, 오류 표시
        if(memberService.hasPhoneNumber(member.getPhoneNumber())){
            bindingResult.reject("hasPhoneNumber",
                    new Object[]{member.getPhoneNumber()}, null);
            log.info("error={}",bindingResult);
            return "members/v1/addMember";
        }

        memberService.save(member);
        return "redirect:/order/v1/login";
    }

}
