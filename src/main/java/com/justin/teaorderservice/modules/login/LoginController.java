package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.modules.login.form.SimpleLoginForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("simpleLoginForm") SimpleLoginForm simpleLoginForm) {
        return "login/simpleLoginForm";
    }

}
