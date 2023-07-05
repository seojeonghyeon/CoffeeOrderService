package com.justin.teaorderservice.order;

import com.justin.teaorderservice.order.form.ItemOrderForm;
import com.justin.teaorderservice.order.form.ItemPurchaseForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/order/v1/teas")
@RequiredArgsConstructor
public class TeaControllerV1 {

    private final TeaRepository teaRepository;
    private final OrderRepository orderRepository;

    @GetMapping
    public String items(Model model){
        List<Tea> teas = teaRepository.findAll();

        List<TeaOrder> teaOrderList = new ArrayList<>();
        for(Tea tea : teas){
            TeaOrder teaOrder = TeaOrder.builder()
                    .id(tea.getId())
                    .teaName(tea.getTeaName())
                    .price(tea.getPrice())
                    .quantity(tea.getQuantity())
                    .orderQuantity(Integer.valueOf(0))
                    .build();
            teaOrderList.add(teaOrder);
        }

        Order order = Order.builder()
                .userId(UUID.randomUUID().toString())
                .teaOrderList(teaOrderList)
                .build();

        model.addAttribute("order",order);
        return "order/v1/addItems";
    }

    @GetMapping("/{teaId}")
    public String tea(@PathVariable long teaId, Model model){
        Tea tea = teaRepository.findById(teaId);
        model.addAttribute("tea", tea);
        return "order/v1/tea";
    }

    @GetMapping("/{orderId}/detail")
    public String orderDetail(@PathVariable long orderId, Model model){
        Order order = orderRepository.findById(orderId);
        model.addAttribute("order",order);
        return "order/v1/order";
    }

    @PostMapping
    public String addOrder(@Validated @ModelAttribute("order") ItemPurchaseForm form, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        //User의 Point에 대한 복합 Rule 검증
        //User가 가지고 있는 Point가 부족한지? 부족하다면 어디에 표시해서 알려줄 것인지
        //글로벌 오류 처리에 대한 표시 부분도 addItems.html에 추가 필요

        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }

        int tea_index = 0;
        List<TeaOrder> teaOrderList = new ArrayList<>();
        List<Tea> teas = teaRepository.findAll();

        for(ItemOrderForm itemOrderForm : form.getTeaOrderList()){
            boolean isNotZeroTheOrderQuantity = itemOrderForm.getOrderQuantity() != 0 ? true : false;
            if(isNotZeroTheOrderQuantity) {
                Tea tea = teas.get(tea_index);
                TeaOrder teaOrder = TeaOrder.builder()
                        .id(tea.getId())
                        .teaName(tea.getTeaName())
                        .price(tea.getPrice())
                        .quantity(tea.getQuantity())
                        .orderQuantity(itemOrderForm.getOrderQuantity())
                        .build();
                teaOrderList.add(teaOrder);
            }
            tea_index++;
        }

        Order order = Order.builder()
                .userId(form.getUserId())
                .teaOrderList(teaOrderList)
                .build();

        //재고수량 감소 -> 안되면 실패 처리 transaction 처리 필요

        Order saveOrder = orderRepository.save(order);
        redirectAttributes.addAttribute("orderId", saveOrder.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/order/v1/teas/{orderId}/detail";
    }




}
