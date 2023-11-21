package com.justin.teaorderservice.modules.ordercount;

import com.justin.teaorderservice.modules.ordercount.OrderCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderCountRepository extends JpaRepository<OrderCount, Long>, OrderCountRepositoryCustom {
    Optional<OrderCount> findByTeaId(Long teaId);
}
