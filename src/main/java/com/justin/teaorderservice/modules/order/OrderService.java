package com.justin.teaorderservice.modules.order;


import com.justin.teaorderservice.modules.tea.TeaOrder;
import java.util.List;

public interface OrderService {
    Order findById(Long orderId);
    Order save(Order order);
    Order saveOrder(String userId, List<TeaOrder> teaOrderList);
}
