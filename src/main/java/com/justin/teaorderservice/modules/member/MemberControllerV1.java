package com.justin.teaorderservice.modules.member;


import com.justin.teaorderservice.modules.member.form.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/order/v1/members")
@RequiredArgsConstructor
public class MemberControllerV1 {

    private final MemberService memberService;


    @GetMapping("/add")
    public String addMember(@ModelAttribute MemberSaveForm memberSaveForm){
        return "members/addMember";
    }

    @PostMapping("/add")
    public String saveMember(@ModelAttribute @Validated MemberSaveForm memberSaveForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "members/addMember";
        }
        Member member = Member.builder()
                .userId(UUID.randomUUID().toString())
                .email(memberSaveForm.getEmail())
                .password(memberSaveForm.getPassword())
                .email(memberSaveForm.getEmail())
                .build();

        memberService.save(member);
        return "redirect:/order/v1/login";
    }

}
