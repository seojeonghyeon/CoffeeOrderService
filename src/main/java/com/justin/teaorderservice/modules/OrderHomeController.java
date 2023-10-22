package com.justin.teaorderservice.modules;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(OrderHomeController.ROOT)
@RequiredArgsConstructor
public class OrderHomeController {

    static final String ROOT = "/view/order/v1/home";
    static final String HOME_PAGE = "order/v1/home";

    @GetMapping
    public String orderHomePage(Model model){
        return HOME_PAGE;
    }

}
