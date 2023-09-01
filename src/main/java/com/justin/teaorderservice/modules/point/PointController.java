package com.justin.teaorderservice.modules.point;

import com.justin.teaorderservice.infra.argumentresolver.Login;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberService;
import com.justin.teaorderservice.modules.point.form.PointAddForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/view/order/v1/points")
@RequiredArgsConstructor
public class PointController {

    private final MemberService memberService;

    @GetMapping("/add")
    public String addPoint(@Login Member loginMember, Model model){
        if(loginMember == null){
            return "redirect:/order/v1/login";
        }
        Member getMember = memberService.findByUserId(loginMember.getUserId());
        PointAddForm pointAddForm = PointAddForm.builder()
                .point(getMember.getPoint())
                .addPoint(Integer.valueOf(0))
                .build();
        model.addAttribute("member","loginMember");
        model.addAttribute("pointAddForm", pointAddForm);
        return "points/v1/addPoint";
    }

}
