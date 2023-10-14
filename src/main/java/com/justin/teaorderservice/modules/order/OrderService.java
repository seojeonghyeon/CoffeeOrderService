package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.tea.TeaRepository;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import com.justin.teaorderservice.modules.teaorder.TeaOrderRepository;
import com.justin.teaorderservice.modules.teaorder.TeaOrderService;
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

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public Order findByUserIdAndId(Long memberId, Long id) {
        Member findMember = memberRepository.findById(memberId).orElse(null);
        return orderRepository.findOrderByMemberAndId(findMember, id).orElse(null);
    }

    @Transactional
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Transactional
    public Long order(Long memberId, TeaOrder... teaOrders) {
        Member findMember = memberRepository.findById(memberId).orElse(null);
        Order order = Order.createOrder(findMember, teaOrders);
        orderRepository.save(order);
        return order.getId();
    }

}
