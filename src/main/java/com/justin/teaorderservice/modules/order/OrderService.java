package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.infra.exception.ErrorCode;
import com.justin.teaorderservice.infra.exception.NoSuchOrderException;
import com.justin.teaorderservice.infra.exception.NotEnoughPointException;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.order.request.RequestItemPurchase;
import com.justin.teaorderservice.modules.tea.TeaRepository;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import com.justin.teaorderservice.modules.teaorder.TeaOrderRepository;
import com.justin.teaorderservice.modules.teaorder.TeaOrderService;
import com.justin.teaorderservice.modules.teaorder.request.RequestItemOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService{

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final TeaOrderService teaOrderService;

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public Order findByUserIdAndId(String memberId, Long id) {
        Member findMember = memberRepository.findById(memberId).orElse(null);
        return orderRepository.findOrderByMemberAndId(findMember, id).orElse(null);
    }

    @Transactional
    public void save(Order order) {
        orderRepository.save(order);
    }

    public String addOrder(Member member, RequestItemPurchase requestItemPurchase){
        List<RequestItemOrder> requestItemOrders = requestItemPurchase.getRequestItemOrderList();
        List<TeaOrder> teaOrders = requestItemOrders.stream()
                .filter(requestItemOrder -> requestItemOrder.getOrderQuantity() != null && requestItemOrder.getOrderQuantity() != 0)
                .map(requestItemOrder -> teaOrderService.teaOrder(requestItemOrder.getId(), requestItemOrder.getPrice(), requestItemOrder.getOrderQuantity()))
                .toList();
        Order saveOrder = this.order(member.getId(), teaOrders.toArray(TeaOrder[]::new));
        if(saveOrder.getStatus() == OrderStatus.REJECTED){
            throw new NotEnoughPointException(ErrorCode.NOT_ENOUGH_POINT);
        }
        return saveOrder.getId().toString();
    }

    @Transactional
    public Order order(String memberId, TeaOrder... teaOrders) {
        Member findMember = memberRepository.findById(memberId).orElse(null);
        Order order = Order.createOrder(findMember, teaOrders);
        if(order.getStatus() == OrderStatus.REJECTED){
            order.cancel();
        }
        return orderRepository.save(order);
    }

    @Transactional
    public void cancel(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new NoSuchOrderException(ErrorCode.NO_SUCH_ORDER));
        order.cancel();
    }

}
