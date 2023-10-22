package com.justin.teaorderservice.modules.point;

import com.justin.teaorderservice.infra.argumentresolver.Login;
import com.justin.teaorderservice.modules.login.session.LoginMember;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberService;
import com.justin.teaorderservice.modules.point.form.PointAddForm;
import com.justin.teaorderservice.modules.point.form.PointResultForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

import static com.justin.teaorderservice.modules.point.PointViewController.ROOT;

/**
 * NAME : Point View Controller V1
 * DESCRIPTION : Point View Controller : V1
 */
@Slf4j
@Controller
@RequestMapping(ROOT)
@RequiredArgsConstructor
public class PointViewController {

    static final String ROOT = "/view/order/v1/points";
    static final String ADD = "/add";
    static final String RESULT_DETAIL = "/{pointId}/detail";
    static final String LOGIN_PAGE = "/order/v1/login";
    static final String ADD_POINT_PAGE = "points/v1/addPoint";
    static final String RESULT_DETAIL_PAGE = "points/v1/addResult";
    static final String REDIRECT_RESULT_DETAIL_URL = "/view/order/v1/points/{pointId}/detail";


    private final PointService pointService;

    /**
     * @param loginMember Session 정보
     * @param model model
     * @return Point 충전 페이지
     */
    @GetMapping(ADD)
    public String points(@Login Member loginMember, Model model){
        if(loginMember == null){
            return "redirect:" + LOGIN_PAGE;
        }
        Integer point = pointService.findPointById(loginMember.getId());
        model.addAttribute("pointAddForm", PointAddForm.createPointAddForm(point, 0));
        return ADD_POINT_PAGE;
    }

    /**
     * @param loginMember Session
     * @param pointAddForm Point 충전 양식
     * @param bindingResult Validation
     * @param redirectAttributes Redirect
     * @return Redirect URL
     */
    @PostMapping(ADD)
    public String addPoint(@Login LoginMember loginMember, @Validated @ModelAttribute("pointAddForm") PointAddForm pointAddForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        if(loginMember == null){
            return "redirect:"+LOGIN_PAGE;
        }
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return ADD_POINT_PAGE;
        }

        String memberId = loginMember.getMemberId();
        Integer point = pointService.findPointById(memberId);
        if(validation(point, pointAddForm.getPoint(), bindingResult)){
            log.info("error={}",bindingResult);
            return ADD_POINT_PAGE;
        }
        Point savePoint = pointService.addPoint(memberId, pointAddForm.getPoint(), pointAddForm.getAddPoint());
        redirectAttributes.addAttribute("pointId", savePoint.getId());
        return "redirect:"+ REDIRECT_RESULT_DETAIL_URL;
    }

    private boolean validation(Integer point, Integer getPoint, BindingResult bindingResult){
        if(!Objects.equals(point, getPoint)){
            bindingResult.reject("noMatchPoint", new Object[]{point, getPoint}, null);
            return true;
        }
        return false;
    }

    /**
     * @param loginMember Session
     * @param model model
     * @return Point 충전 결과 페이지
     */
    @GetMapping(RESULT_DETAIL)
    public String pointDetail(@Login LoginMember loginMember, @PathVariable long pointId, Model model){
        Point point = pointService.findPointByMemberAndPointId(loginMember.getMemberId(), pointId);
        PointResultForm pointResultForm = PointResultForm.createPointResultForm(point);
        model.addAttribute("pointResultForm", pointResultForm);
        return RESULT_DETAIL_PAGE;
    }

}
