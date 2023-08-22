package com.justin.teaorderservice.modules.member;


import com.justin.teaorderservice.modules.member.form.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/view/order/v1/members")
@RequiredArgsConstructor
public class MemberControllerV1 {

    private final MemberService memberService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ConversionService conversionService;

    @GetMapping("/add")
    public String addMember(@ModelAttribute MemberSaveForm memberSaveForm){
        return "members/v1/addMember";
    }

    @PostMapping("/add")
    public String saveMember(@ModelAttribute @Validated MemberSaveForm memberSaveForm, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "members/v1/addMember";
        }

        memberSaveForm.encodePassword(passwordEncoder.encode(memberSaveForm.getPassword()), passwordEncoder.encode(memberSaveForm.getSimplePassword()));
        Member member = conversionService.convert(memberSaveForm, Member.class);

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
