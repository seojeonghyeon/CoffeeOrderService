package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.infra.argumentresolver.Login;
import com.justin.teaorderservice.modules.login.session.LoginMember;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberService;
import com.justin.teaorderservice.modules.tea.TeaService;
import com.justin.teaorderservice.modules.teaorder.TeaOrderService;
import com.justin.teaorderservice.modules.teaorder.form.ItemOrderForm;
import com.justin.teaorderservice.modules.order.form.ItemPurchaseForm;
import com.justin.teaorderservice.modules.tea.Tea;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;

import static com.justin.teaorderservice.modules.order.OrderViewController.ROOT;

/**
 * NAME : Order View Controller V1
 * DESCRIPTION : Order View Controller : V1
 */
@Slf4j
@Controller
@RequestMapping(ROOT)
@RequiredArgsConstructor
public class OrderViewController {

    static final String ROOT = "/view/order/v1/orders";
    static final String LOGIN_PAGE = "/order/v1/login";
    static final String ADD_ITEMS_PAGE = "order/v1/addItems";
    static final String ORDER_DETAIL = "/{orderId}/detail";
    static final String ORDER_DETAIL_PAGE = "order/v1/order";


    private final OrderService orderService;
    private final TeaOrderService teaOrderService;
    private final MemberService memberService;
    private final TeaService teaService;

    /**
     * @param loginMember 사용자 Session
     * @param model model
     * @return 주문 페이지
     */
    @GetMapping
    public String items(@Login LoginMember loginMember, Model model){
        if(loginMember == null){
            return "redirect:"+LOGIN_PAGE;
        }
        String memberName = memberService.findMemberNameByMemberId(loginMember.getMemberId());
        List<Tea> teas = teaService.findAll();
        List<ItemOrderForm> itemOrderForms = teas.stream().map(ItemOrderForm::createItemOrderForm).toList();
        ItemPurchaseForm itemPurchaseForm = ItemPurchaseForm.createItemPurchaseForm(memberName, itemOrderForms);
        model.addAttribute("member", loginMember);
        model.addAttribute("itemPurchaseForm",itemPurchaseForm);
        return ADD_ITEMS_PAGE;
    }

    /**
     * @param orderId 주문 ID
     * @param model model
     * @return 주문 내역 확인 페이지
     */
    @GetMapping(ORDER_DETAIL)
    public String orderDetail(@Login LoginMember loginMember, @PathVariable long orderId, Model model) {
        Order order = orderService.findByUserIdAndId(loginMember.getMemberId(), orderId);
        if (order != null) {
            List<TeaOrder> teaOrders = teaOrderService.findByOrderId(orderId);
            List<ItemOrderForm> itemOrderFormList = teaOrders.stream()
                    .map(ItemOrderForm::createItemOrderForm)
                    .collect(Collectors.toList());
            ItemPurchaseForm itemPurchaseForm = ItemPurchaseForm.createItemPurchaseForm(orderId, loginMember.getMemberId(), itemOrderFormList);
            model.addAttribute("itemPurchaseForm", itemPurchaseForm);
        }
        return ORDER_DETAIL_PAGE;
    }

    /**
     * @param itemPurchaseForm 주문 양식
     * @param bindingResult Validation
     * @param redirectAttributes Redirect
     * @return 주문 내역 확인
     */
    @PostMapping
    public String addOrder(@Login LoginMember loginMember, @Validated @ModelAttribute("itemPurchaseForm") ItemPurchaseForm itemPurchaseForm, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            log.info("error={}",bindingResult);
            return ADD_ITEMS_PAGE;
        }

        String memberId = loginMember.getMemberId();
        List<ItemOrderForm> itemOrderFormList = itemPurchaseForm.getItemOrderFormList();
        List<TeaOrder> teaOrders = itemOrderFormList.stream()
                .filter(itemOrderForm -> itemOrderForm.getOrderQuantity() != null && itemOrderForm.getOrderQuantity() != 0)
                .map(itemOrderForm -> teaOrderService.teaOrder(itemOrderForm.getId(), itemOrderForm.getPrice(), itemOrderForm.getOrderQuantity()))
                .toList();
        Long orderId = orderService.order(memberId, teaOrders.toArray(TeaOrder[]::new));

        redirectAttributes.addAttribute("orderId", orderId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:"+ROOT+ORDER_DETAIL;
    }


}
