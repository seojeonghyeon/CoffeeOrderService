package com.justin.teaorderservice.modules.tea;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * NAME : Tea View Controller V1
 * DESCRIPTION : Tea View Controller : V1
 */
@Slf4j
@Controller
@RequestMapping("/view/order/v1/teas")
@RequiredArgsConstructor
public class TeaViewController {

    private final TeaService teaService;

    /**
     * @param teaId Tea ID
     * @param model model
     * @return Tea 상세 페이지
     */
    @GetMapping("/{teaId}")
    public String tea(@PathVariable long teaId, Model model){
        Tea tea = teaService.findById(teaId);
        model.addAttribute("tea", tea);
        return "tea/v1/tea";
    }

}
