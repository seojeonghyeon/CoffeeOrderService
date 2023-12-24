package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.modules.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderByMemberAndId(Member member, Long id);
    List<Order> findByMemberAndStatus(Member member, OrderStatus status);
}
