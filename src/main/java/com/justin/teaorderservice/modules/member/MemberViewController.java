package com.justin.teaorderservice.modules.member;


import com.justin.teaorderservice.modules.member.form.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

/**
 * NAME : Member View Controller V1
 * DESCRIPTION : Member View Controller : V1
 */
@Slf4j
@Controller
@RequestMapping("/view/order/v1/members")
@RequiredArgsConstructor
public class MemberViewController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @GetMapping("/add")
    public String addMember(@ModelAttribute MemberSaveForm memberSaveForm){
        return "members/v1/addMember";
    }

    /**
     *
     * @param memberSaveForm 회원 가입 양식
     * @param bindingResult Validation
     * @param redirectAttributes Redirect
     * @return 회원 가입 내역 확인
     */
    @PostMapping("/add")
    public String saveMember(@ModelAttribute @Validated MemberSaveForm memberSaveForm, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "members/v1/addMember";
        }

        memberSaveForm.encodePassword(passwordEncoder.encode(memberSaveForm.getPassword()), passwordEncoder.encode(memberSaveForm.getSimplePassword()));
        Member member = modelMapper.map(memberSaveForm, Member.class);
        member.setUser(UUID.randomUUID().toString());

        if(memberService.hasPhoneNumber(member.getPhoneNumber())){
            bindingResult.reject("hasPhoneNumber",
                    new Object[]{member.getPhoneNumber()}, null);
            log.info("error={}",bindingResult);
            return "members/v1/addMember";
        }

        Member saveMember = memberService.save(member);
        redirectAttributes.addAttribute("userId", saveMember.getUserId());
        return "redirect:/view/order/v1/members/{userId}/detail";
    }

    /**
     *
     * @param userId 사용자 ID
     * @param model model
     * @return 회원 가입 내역 확인 페이지
     */
    @GetMapping("/{userId}/detail")
    public String memberDetail(@PathVariable String userId, Model model){
        Member member = memberService.findByUserId(userId);
        log.info("get: member={}", member);
        String phoneNumber;
        if(member == null) phoneNumber = "가입 정보가 존재하지 않습니다";
        else phoneNumber = member.getPhoneNumber();

        model.addAttribute("phoneNumber", phoneNumber);
        return "members/v1/addResult";
    }

}
