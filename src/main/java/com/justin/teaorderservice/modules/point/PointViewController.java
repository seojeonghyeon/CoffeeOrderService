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

/**
 * NAME : Point View Controller V1
 * DESCRIPTION : Point View Controller : V1
 */
@Slf4j
@Controller
@RequestMapping("/view/order/v1/points")
@RequiredArgsConstructor
public class PointViewController {

    private final PointService pointService;
    private final MemberService memberService;

    /**
     * @param loginMember Session 정보
     * @param model model
     * @return Point 충전 페이지
     */
    @GetMapping("/add")
    public String points(@Login Member loginMember, Model model){
        if(loginMember == null){
            return "redirect:/order/v1/login";
        }
        Integer point = pointService.findPointById(loginMember.getMemberId());
        PointAddForm pointAddForm = PointAddForm.builder()
                .point(point)
                .addPoint(Integer.valueOf(0))
                .build();
        model.addAttribute("pointAddForm", pointAddForm);
        return "points/v1/addPoint";
    }

    /**
     * @param loginMember Session
     * @param pointAddForm Point 충전 양식
     * @param bindingResult Validation
     * @param redirectAttributes Redirect
     * @return Redirect URL
     */
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

        Integer point = pointService.findPointById(loginMember.getMemberId());
        validation(point, pointAddForm.getPoint(), bindingResult);
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }
        loginMember.setPoint(point+pointAddForm.getAddPoint());
        Member saveMember = memberService.save(loginMember);

        redirectAttributes.addAttribute("point", saveMember.getPoint());
        return "redirect:/view/order/v1/points/detail";

    }

    private void validation(Integer point, Integer getPoint, BindingResult bindingResult){
        if(point != getPoint){
            bindingResult.reject("noMatchPoint", new Object[]{point, getPoint}, null);
        }
    }

    /**
     * @param loginMember Session
     * @param model model
     * @return Point 충전 결과 페이지
     */
    @GetMapping("/detail")
    public String pointDetail(@Login Member loginMember, Model model){
        Integer point = pointService.findPointById(loginMember.getMemberId());
        model.addAttribute("point", point);
        return "points/v1/addResult";
    }

}
