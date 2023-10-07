package com.justin.teaorderservice.modules.teaorder;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeaOrderRepository extends CrudRepository<TeaOrder, Long> {
    List<TeaOrder> findByOrderId(Long orderId);
}
