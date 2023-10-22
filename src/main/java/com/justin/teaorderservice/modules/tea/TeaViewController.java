package com.justin.teaorderservice.modules.tea;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.justin.teaorderservice.modules.tea.TeaViewController.*;

/**
 * NAME : Tea View Controller V1
 * DESCRIPTION : Tea View Controller : V1
 */
@Slf4j
@Controller
@RequestMapping(ROOT)
@RequiredArgsConstructor
public class TeaViewController {

    static final String ROOT = "/view/order/v1/teas";
    static final String TEA_DETAIL = "/{teaId}";
    static final String TEA_DETAIL_PAGE = "tea/v1/tea";


    private final TeaService teaService;

    /**
     * @param teaId Tea ID
     * @param model model
     * @return Tea 상세 페이지
     */
    @GetMapping(TEA_DETAIL)
    public String tea(@PathVariable long teaId, Model model){
        Tea tea = teaService.findById(teaId);
        model.addAttribute("tea", tea);
        return TEA_DETAIL_PAGE;
    }

}
