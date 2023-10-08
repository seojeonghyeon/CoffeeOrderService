package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import com.justin.teaorderservice.modules.teaorder.TeaOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService{
    private final OrderRepository orderRepository;
    private final TeaOrderService teaOrderService;

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public Order findByUserIdAndId(String userId, Long id) {
        return orderRepository.findByUserIdAndId(userId, id).filter(order -> order.getDisabled()==false).orElse(null);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public Order order(String memberId, List<TeaOrder> teaOrderList) {

    }

}
