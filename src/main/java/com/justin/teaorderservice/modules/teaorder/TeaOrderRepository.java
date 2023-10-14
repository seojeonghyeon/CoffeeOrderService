package com.justin.teaorderservice.modules.teaorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeaOrderRepository extends JpaRepository<TeaOrder, Long> {
    List<TeaOrder> findByOrderId(Long orderId);
}
