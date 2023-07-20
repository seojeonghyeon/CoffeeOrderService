package com.justin.teaorderservice.modules.order;

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

    private final TeaRepository teaRepository;
    private final OrderRepository orderRepository;

    @GetMapping
    public String items(Model model){
        List<Tea> teas = teaRepository.findAll();

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
                .userId(UUID.randomUUID().toString())
                .itemOrderFormList(itemOrderFormList)
                .build();

        model.addAttribute("itemPurchaseForm",itemPurchaseForm);
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
    public String addOrder(@Validated @ModelAttribute("itemPurchaseForm") ItemPurchaseForm itemPurchaseForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        /**
         * 오류가 존재할 경우
         */
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }

        List<Tea> teas = teaRepository.findAll();
        List<TeaOrder> teaOrderList = new ArrayList<>();
        List<ItemOrderForm> itemOrderFormList = itemPurchaseForm.getItemOrderFormList();


        int tea_max = teas.size();

        /**
         * Transactional
         */
        for(int i = 0; i < tea_max; ++i){
            ItemOrderForm itemOrderForm = itemOrderFormList.get(i);
            boolean isNotZeroTheOrderQuantity = itemOrderForm.getOrderQuantity() != 0;
            if(isNotZeroTheOrderQuantity) {
                Tea tea = teas.get(i);
                Integer remaining = tea.getQuantity() - itemOrderForm.getOrderQuantity();
                boolean isNoRemaining = remaining <= 0;

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

                /**
                 * 오류가 존재할 경우
                 */
                if(bindingResult.hasErrors()){
                    log.info("error={}",bindingResult);
                    return "order/v1/addItems";
                }

                /**
                 * 재고 감소
                 */
                TeaOrder teaOrder = TeaOrder.builder()
                        .id(tea.getId())
                        .teaName(tea.getTeaName())
                        .price(tea.getPrice())
                        .quantity(remaining)
                        .orderQuantity(itemOrderForm.getOrderQuantity())
                        .build();
                teaOrderList.add(teaOrder);
                tea.setQuantity(remaining);
                teaRepository.update(tea.getId(), tea);
            }
        }

        /**
         * 주문 저장
         */
        Order order = Order.builder()
                .userId(itemPurchaseForm.getUserId())
                .teaOrderList(teaOrderList)
                .build();
        Order saveOrder = orderRepository.save(order);
        redirectAttributes.addAttribute("orderId", saveOrder.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/order/v1/teas/{orderId}/detail";
    }




}
