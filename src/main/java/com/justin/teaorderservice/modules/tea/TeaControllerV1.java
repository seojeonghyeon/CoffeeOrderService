package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.infra.argumentresolver.Login;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.order.Order;
import com.justin.teaorderservice.modules.order.OrderService;
import com.justin.teaorderservice.modules.order.form.ItemOrderForm;
import com.justin.teaorderservice.modules.order.form.ItemPurchaseForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/view/order/v1/teas")
@RequiredArgsConstructor
public class TeaControllerV1 {

    private final TeaService teaService;
    private final OrderService orderService;
    private final ConversionService conversionService;

    @GetMapping
    public String items(@Login Member loginMember, Model model){

        if(loginMember == null){
            return "redirect:/order/v1/login";
        }

        List<Tea> teas = teaService.findAll();
        List<ItemOrderForm> itemOrderFormList = conversionService.convert(teas, List.class);
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
        ItemPurchaseForm itemPurchaseForm = conversionService.convert(order, ItemPurchaseForm.class);
        model.addAttribute("itemPurchaseForm",itemPurchaseForm);
        return "order/v1/order";
    }

    @PostMapping
    public String addOrder(@Validated @ModelAttribute("itemPurchaseForm") ItemPurchaseForm itemPurchaseForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        /* Validation 상의 오류가 존재할 경우 */
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }

        Order order = conversionService.convert(itemPurchaseForm, Order.class);
        List<Tea> teas = teaService.findAll();
        List<TeaOrder> teaOrderList = order.getTeaOrderList();

        int tea_max = teas.size();

        for(int i = 0; i < tea_max; ++i){
            TeaOrder teaOrder = teaOrderList.get(i);
            boolean isNotZeroTheOrderQuantity = teaOrder.getOrderQuantity() != 0;
            if(isNotZeroTheOrderQuantity) {
                Tea tea = teas.get(i);
                Integer remaining = tea.getQuantity() - teaOrder.getOrderQuantity();
                boolean isNoRemaining = remaining < 0;

                /* 재고가 없을 경우 */
                if(isNoRemaining){
                    bindingResult.reject("noRemaining",
                            new Object[]{teaOrder.getOrderQuantity(), tea.getQuantity()}, null);
                }

                /* 사용자의 Point가 없을 경우 */
                //추가 필요
            }
        }

        /* Process 처리 상의 오류가 존재할 경우 */
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }

        Order saveOrder = orderService.saveOrder(order.getUserId(), teaOrderList);

        redirectAttributes.addAttribute("orderId", saveOrder.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/view/order/v1/teas/{orderId}/detail";
    }




}
