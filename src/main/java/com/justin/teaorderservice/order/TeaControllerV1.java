package com.justin.teaorderservice.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order/v1/teas")
@RequiredArgsConstructor
public class TeaControllerV1 {

    private final TeaRepository teaRepository;

    @GetMapping
    public String items(Model model){
        List<Tea> teas = teaRepository.findAll();
        List<TeaOrder> teaOrders = new ArrayList<>();
        for(Tea tea : teas){
            TeaOrder teaOrder = TeaOrder.builder()
                    .id(tea.getId())
                    .teaName(tea.getTeaName())
                    .price(tea.getPrice())
                    .quantity(tea.getQuantity())
                    .orderQuantity(Integer.valueOf(0))
                    .build();
            teaOrders.add(teaOrder);
        }
        model.addAttribute("teaOrders",teaOrders);
        return "order/v1/addItems";
    }

}
