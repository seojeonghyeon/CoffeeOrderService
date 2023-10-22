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

        if(memberService.hasEmail(memberSaveForm.getEmail())){
            bindingResult.reject("existEmail",
                    new Object[]{memberSaveForm.getEmail()}, null);
            log.info("error={}",bindingResult);
            return "members/v1/addMember";
        }

        String memberId = memberService.register(memberSaveForm.getEmail(), passwordEncoder.encode(memberSaveForm.getPassword()), passwordEncoder.encode(memberSaveForm.getSimplePassword()));
        redirectAttributes.addAttribute("memberId", memberId);
        return "redirect:/view/order/v1/members/{memberId}/detail";
    }

    /**
     *
     * @param memberId 사용자 ID
     * @param model model
     * @return 회원 가입 내역 확인 페이지
     */
    @GetMapping("/{memberId}/detail")
    public String memberDetail(@PathVariable("memberId") String memberId, Model model){
        Member member = memberService.findByMemberId(memberId);
        String email;
        if(member == null) email = "가입 정보가 존재 하지 않습니다";
        else email = member.getEmail();

        model.addAttribute("email", email);
        return "members/v1/addResult";
    }

}
