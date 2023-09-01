package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.infra.argumentresolver.Login;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.order.form.ItemOrderForm;
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

@Slf4j
@Controller
@RequestMapping("/view/order/v1/orders")
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final TeaService teaService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @GetMapping
    public String items(@Login Member loginMember, Model model){

        if(loginMember == null){
            return "redirect:/order/v1/login";
        }

        List<Tea> teas = teaService.findAll();
        List<ItemOrderForm> itemOrderFormList = new ArrayList<>();
        teas.stream().forEach(tea -> itemOrderFormList.add(modelMapper.map(tea, ItemOrderForm.class)));

        ItemPurchaseForm itemPurchaseForm = ItemPurchaseForm.builder()
                .userId(loginMember.getUserId())
                .itemOrderFormList(itemOrderFormList)
                .build();

        model.addAttribute("member", loginMember);
        model.addAttribute("itemPurchaseForm",itemPurchaseForm);
        return "order/v1/addItems";
    }

    @GetMapping("/{orderId}/detail")
    public String orderDetail(@PathVariable long orderId, Model model){
        Order order = orderService.findById(orderId);
        ItemPurchaseForm itemPurchaseForm = modelMapper.map(order, ItemPurchaseForm.class);
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

        List<ItemOrderForm> itemOrderFormList = itemPurchaseForm.getItemOrderFormList();
        List<TeaOrder> teaOrderList = new ArrayList<>();
        itemOrderFormList.stream().forEach(order -> {
            if(order.getOrderQuantity() != null && order.getOrderQuantity() != 0){
                teaOrderList.add(modelMapper.map(order, TeaOrder.class));
            }
        });

        for(TeaOrder teaOrder : teaOrderList){
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

        /* Process 처리 상의 오류가 존재할 경우 */
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return "order/v1/addItems";
        }

        Order saveOrder = orderService.saveOrder(itemPurchaseForm.getUserId(), teaOrderList);

        redirectAttributes.addAttribute("orderId", saveOrder.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/view/order/v1/orders/{orderId}/detail";
    }
}
