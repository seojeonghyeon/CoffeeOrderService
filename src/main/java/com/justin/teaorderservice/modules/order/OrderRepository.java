package com.justin.teaorderservice.modules.order;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OrderRepository {
    private static final Map<Long, Order> store = new HashMap<>();
    private static long sequence =0L;

    public Order save(Order order){
        order.setId(++sequence);
        store.put(order.getId(), order);
        return order;
    }

    public Order findById(Long id){
        return store.get(id);
    }
    public Optional<Order> findUserIdAndId(String userId, Long id){
        return findAll().stream()
                .filter(order -> order.getId().equals(id))
                .filter(order -> order.getUserId().equals(userId))
                .findFirst();
    }
    public List<Order> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
