package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.modules.tea.Tea;
import com.justin.teaorderservice.modules.tea.TeaOrder;
import com.justin.teaorderservice.modules.tea.TeaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final TeaService teaService;

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    @Override
    public Order saveOrder(String userId, List<TeaOrder> teaOrderList) {
        int orderSize = teaOrderList.size();
        /**
         * 재고 감소
         */
        for(int i = 0; i < orderSize; ++i){
            TeaOrder teaOrder = teaOrderList.get(i);
            boolean isNotZeroTheOrderQuantity = teaOrder.getOrderQuantity() != 0;
            if(isNotZeroTheOrderQuantity) {
                Tea tea = teaService.findById(teaOrder.getId());
                Integer remaining = tea.getQuantity() - teaOrder.getOrderQuantity();
                teaOrder.setOrderQuantity(teaOrder.getOrderQuantity());
                tea.setQuantity(remaining);
                teaService.update(tea.getId(), tea);
            }
        }
        /**
         * 사용자 Point 감소
         */

        /**
         * 주문 저장
         */
        Order order = Order.builder()
                .userId(userId)
                .teaOrderList(teaOrderList)
                .build();
        Order saveOrder = this.save(order);
        return saveOrder;
    }
}
