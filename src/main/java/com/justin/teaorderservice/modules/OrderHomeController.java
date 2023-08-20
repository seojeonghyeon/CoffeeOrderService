package com.justin.teaorderservice.modules;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/view/order/v1/home")
@RequiredArgsConstructor
public class OrderHomeController {

    @GetMapping
    public String orderHomePage(Model model){
        return "order/v1/home";
    }

}
