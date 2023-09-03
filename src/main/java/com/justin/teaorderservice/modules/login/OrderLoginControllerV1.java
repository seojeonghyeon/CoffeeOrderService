package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.modules.login.form.SimpleLoginForm;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.infra.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/view/order/v1/login")
@RequiredArgsConstructor
public class OrderLoginControllerV1 {

    private final LoginService loginService;

    @GetMapping
    public String loginForm(@ModelAttribute("simpleLoginForm") SimpleLoginForm simpleLoginForm) {
        return "login/v1/simpleLoginForm";
    }

    @PostMapping
    public String simpleLoginV1(@Validated @ModelAttribute("simpleLoginForm") SimpleLoginForm simpleLoginForm, BindingResult bindingResult,
                                HttpServletRequest request, @RequestParam(defaultValue = "/view/order/v1/orders") String redirectURL){
        if(bindingResult.hasErrors()){
            return "login/v1/simpleLoginForm";
        }

        Member member = loginService.simpleLogin(
                simpleLoginForm.getPhoneNumber(),
                simpleLoginForm.getSimplePassword()
        );

        if(member == null){
            bindingResult.reject("loginFail", new Object[]{}, null);
            return "login/v1/simpleLoginForm";
        }else if(member.getDisabled()){
            bindingResult.reject("isDisabled", new Object[]{}, null);
            return "login/v1/simpleLoginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return "redirect:"+redirectURL;
    }

    @PostMapping("/logout")
    public String logoutV1(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
        return "redirect:/view/order/v1/home";
    }

}

