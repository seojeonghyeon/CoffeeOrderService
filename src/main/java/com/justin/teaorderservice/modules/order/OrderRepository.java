package com.justin.teaorderservice.modules.order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByUserIdAndId(String userId, Long id);
}
