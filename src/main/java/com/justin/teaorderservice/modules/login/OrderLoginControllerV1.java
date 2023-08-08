package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.modules.login.form.SimpleLoginForm;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.session.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order/v1/login")
public class OrderLoginControllerV1 {

    private final LoginService loginService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping
    public String loginForm(@ModelAttribute("simpleLoginForm") SimpleLoginForm simpleLoginForm) {
        return "login/simpleLoginForm";
    }

    @PostMapping
    public String simpleLoginV1(@Validated @ModelAttribute SimpleLoginForm simpleLoginForm, BindingResult bindingResult, @RequestParam(defaultValue = "/order/v1/login")String redirectURL, HttpServletRequest request){
        if(bindingResult.hasErrors()){
            return "/order/v1/login";
        }

        Member member = loginService.simpleLogin(simpleLoginForm.getPhoneNumber(), passwordEncoder.encode(simpleLoginForm.getSimplePassword()));

        if(member == null){
            bindingResult.reject("loginFail", new Object[]{}, null);
            return "/order/v1/login";
        }else if(member.isDisabled()){
            bindingResult.reject("isDisabled", new Object[]{}, null);
            return "/order/v1/login";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);
        return "redirect:" + redirectURL;
    }

}

