package com.justin.teaorderservice.modules.point;

import com.justin.teaorderservice.infra.argumentresolver.Login;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberService;
import com.justin.teaorderservice.modules.point.form.PointAddForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/view/order/v1/points")
@RequiredArgsConstructor
public class PointController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String points(@Login Member loginMember, Model model){
        if(loginMember == null){
            return "redirect:/order/v1/login";
        }
        Member getMember = memberService.findByUserId(loginMember.getUserId());
        PointAddForm pointAddForm = PointAddForm.builder()
                .point(getMember.getPoint())
                .addPoint(Integer.valueOf(0))
                .build();
        model.addAttribute("pointAddForm", pointAddForm);
        return "points/v1/addPoint";
    }

    @PostMapping("/add")
    public String addPoint(@Login Member loginMember, @Validated @ModelAttribute("pointAddForm") PointAddForm pointAddForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        if(loginMember == null){
            return "redirect:/order/v1/login";
        }
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }

        Member member = memberService.findByUserId(loginMember.getUserId());
        validation(member, pointAddForm, bindingResult);
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }
        member.setPoint(member.getPoint()+pointAddForm.getAddPoint());
        Member saveMember = memberService.save(member);

        redirectAttributes.addAttribute("point", saveMember.getPoint());
        return "redirect:/view/order/v1/points/detail";

    }

    private void validation(Member member, PointAddForm pointAddForm, BindingResult bindingResult){
        if(member.getPoint() != pointAddForm.getPoint()){
            bindingResult.reject("noMatchPoint", new Object[]{member.getPoint(), pointAddForm.getPoint()}, null);
        }
    }

    @GetMapping("/detail")
    public String pointDetail(@Login Member loginMember, Model model){
        Member member = memberService.findByUserId(loginMember.getUserId());
        Integer point;
        if(member == null) point = 0;
        else point = member.getPoint();
        model.addAttribute("point", point);
        return "points/v1/addResult";
    }

}
