package com.justin.teaorderservice.modules.teaorder;

import java.util.List;

public interface TeaOrderService {
    List<TeaOrder> findByOrderId(Long orderId);
    TeaOrder findById(Long id);
    TeaOrder save(TeaOrder teaOrder);
    void update(String userId, TeaOrder teaOrder);
}
