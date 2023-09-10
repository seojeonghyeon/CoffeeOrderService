package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.infra.argumentresolver.Login;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.teaorder.TeaOrderService;
import com.justin.teaorderservice.modules.teaorder.form.ItemOrderForm;
import com.justin.teaorderservice.modules.order.form.ItemPurchaseForm;
import com.justin.teaorderservice.modules.tea.Tea;
import com.justin.teaorderservice.modules.tea.TeaService;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * NAME : Order View Controller V1
 * DESCRIPTION : Order View Controller : V1
 */
@Slf4j
@Controller
@RequestMapping("/view/order/v1/orders")
@RequiredArgsConstructor
public class OrderViewControllerV1 {

    private final TeaService teaService;
    private final OrderService orderService;
    private final TeaOrderService teaOrderService;
    private final ModelMapper modelMapper;

    /**
     * @param loginMember 사용자 Session
     * @param model model
     * @return 주문 페이지
     */
    @GetMapping
    public String items(@Login Member loginMember, Model model){

        if(loginMember == null){
            return "redirect:/order/v1/login";
        }

        List<Tea> teas = teaService.findAll();
        List<ItemOrderForm> itemOrderFormList = new ArrayList<>();
        teas.forEach(tea -> itemOrderFormList.add(modelMapper.map(tea, ItemOrderForm.class)));

        ItemPurchaseForm itemPurchaseForm = ItemPurchaseForm.builder()
                .userId(loginMember.getUserId())
                .itemOrderFormList(itemOrderFormList)
                .build();

        model.addAttribute("member", loginMember);
        model.addAttribute("itemPurchaseForm",itemPurchaseForm);
        return "order/v1/addItems";
    }

    /**
     * @param orderId 주문 ID
     * @param model model
     * @return 주문 내역 확인 페이지
     */
    @GetMapping("/{orderId}/detail")
    public String orderDetail(@Login Member loginMember, @PathVariable long orderId, Model model) {
        Order order = orderService.findByUserIdAndId(loginMember.getUserId(), orderId);
        if (order != null) {
            List<TeaOrder> teaOrderList = teaOrderService.findByOrderId(orderId);
            ItemPurchaseForm itemPurchaseForm = modelMapper.map(order, ItemPurchaseForm.class);
            List<ItemOrderForm> itemOrderFormList = new ArrayList<>();
            teaOrderList.forEach(teaOrder -> {
                ItemOrderForm itemOrderForm = ItemOrderForm.builder()
                        .id(teaOrder.getTeaId())
                        .orderQuantity(teaOrder.getOrderQuantity())
                        .price(teaOrder.getPrice())
                        .quantity(teaOrder.getQuantity())
                        .teaName(teaOrder.getTeaName())
                        .build();
                itemOrderFormList.add(itemOrderForm);
            });
            itemPurchaseForm.setItemOrderFormList(itemOrderFormList);
            model.addAttribute("itemPurchaseForm", itemPurchaseForm);
        }
        return "order/v1/order";
    }

    /**
     * @param itemPurchaseForm 주문 양식
     * @param bindingResult Validation
     * @param redirectAttributes Redirect
     * @return 주문 내역 확인
     */
    @PostMapping
    public String addOrder(@Validated @ModelAttribute("itemPurchaseForm") ItemPurchaseForm itemPurchaseForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }

        String userId = itemPurchaseForm.getUserId();
        List<ItemOrderForm> itemOrderFormList = itemPurchaseForm.getItemOrderFormList();
        List<TeaOrder> teaOrderList = new ArrayList<>();
        itemOrderFormList.forEach(itemOrderForm -> {
            if(itemOrderForm.getOrderQuantity() != null && itemOrderForm.getOrderQuantity() != 0){
                TeaOrder teaOrder = TeaOrder.builder()
                        .teaId(itemOrderForm.getId())
                        .orderQuantity(itemOrderForm.getOrderQuantity())
                        .quantity(itemOrderForm.getQuantity())
                        .price(itemOrderForm.getPrice())
                        .teaName(itemOrderForm.getTeaName())
                        .disabled(false)
                        .build();
                teaOrderList.add(teaOrder);
            }
        });

        teaOrderList.forEach(teaOrder -> validation(userId, teaOrder, bindingResult));

        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }

        Order saveOrder = orderService.saveOrder(userId, teaOrderList);

        redirectAttributes.addAttribute("orderId", saveOrder.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/view/order/v1/orders/{orderId}/detail";
    }

    private void validation(String userId, TeaOrder teaOrder, BindingResult bindingResult){
        Tea tea = teaService.findById(teaOrder.getTeaId());
        if(tea != null){
            boolean isNoRemaining = tea.getQuantity() - teaOrder.getOrderQuantity() < 0;
            if(isNoRemaining){
                bindingResult.reject("noRemaining",
                        new Object[]{teaOrder.getOrderQuantity(), tea.getQuantity()}, null);
            }
            /* Point가 없는 경우 */
        }else{
            bindingResult.reject("noTea",
                    new Object[]{}, null);
        }
    }

}
