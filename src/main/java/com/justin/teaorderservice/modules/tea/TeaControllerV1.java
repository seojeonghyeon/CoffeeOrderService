package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.modules.argumentresolver.Login;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.order.Order;
import com.justin.teaorderservice.modules.order.OrderService;
import com.justin.teaorderservice.modules.order.form.ItemOrderForm;
import com.justin.teaorderservice.modules.order.form.ItemPurchaseForm;
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

    private final TeaService teaService;
    private final OrderService orderService;

    @GetMapping
    public String items(@Login Member loginMember, Model model){

        if(loginMember == null){
            return "redirect:/order/v1/login";
        }

        List<Tea> teas = teaService.findAll();

        List<ItemOrderForm> itemOrderFormList = new ArrayList<>();
        for(Tea tea : teas){
            ItemOrderForm itemOrderForm = ItemOrderForm.builder()
                    .id(tea.getId())
                    .teaName(tea.getTeaName())
                    .price(tea.getPrice())
                    .quantity(tea.getQuantity())
                    .orderQuantity(Integer.valueOf(0))
                    .build();
            itemOrderFormList.add(itemOrderForm);
        }

        ItemPurchaseForm itemPurchaseForm = ItemPurchaseForm.builder()
                .userId(loginMember.getUserId())
                .itemOrderFormList(itemOrderFormList)
                .build();

        model.addAttribute("member", loginMember);
        model.addAttribute("itemPurchaseForm",itemPurchaseForm);
        return "order/v1/addItems";
    }

    @GetMapping("/{teaId}")
    public String tea(@PathVariable long teaId, Model model){
        Tea tea = teaService.findById(teaId);
        model.addAttribute("tea", tea);
        return "order/v1/tea";
    }

    @GetMapping("/{orderId}/detail")
    public String orderDetail(@PathVariable long orderId, Model model){
        Order order = orderService.findById(orderId);
        model.addAttribute("order",order);
        return "order/v1/order";
    }

    @PostMapping
    public String addOrder(@Validated @ModelAttribute("itemPurchaseForm") ItemPurchaseForm itemPurchaseForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        /**
         * Validation 상의 오류가 존재할 경우
         */
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }

        List<Tea> teas = teaService.findAll();
        List<TeaOrder> teaOrderList = new ArrayList<>();
        List<ItemOrderForm> itemOrderFormList = itemPurchaseForm.getItemOrderFormList();

        int tea_max = teas.size();

        for(int i = 0; i < tea_max; ++i){
            ItemOrderForm itemOrderForm = itemOrderFormList.get(i);
            boolean isNotZeroTheOrderQuantity = itemOrderForm.getOrderQuantity() != 0;
            if(isNotZeroTheOrderQuantity) {
                Tea tea = teas.get(i);
                Integer remaining = tea.getQuantity() - itemOrderForm.getOrderQuantity();
                boolean isNoRemaining = remaining < 0;

                /**
                 * 재고가 없을 경우
                 */
                if(isNoRemaining){
                    bindingResult.reject("noRemaining",
                            new Object[]{itemOrderForm.getOrderQuantity(), tea.getQuantity()}, null);
                }

                /**
                 * 사용자의 Point가 없을 경우
                 */
                //추가 필요

                TeaOrder teaOrder = TeaOrder.builder()
                        .id(tea.getId())
                        .teaName(tea.getTeaName())
                        .quantity(tea.getQuantity())
                        .orderQuantity(itemOrderForm.getOrderQuantity())
                        .price(tea.getPrice())
                        .build();
                teaOrderList.add(teaOrder);
            }
        }

        /**
         * Process 처리 상의 오류가 존재할 경우
         */
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }

        Order saveOrder = orderService.saveOrder(itemPurchaseForm.getUserId(), teaOrderList);

        redirectAttributes.addAttribute("orderId", saveOrder.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/order/v1/teas/{orderId}/detail";
    }




}
