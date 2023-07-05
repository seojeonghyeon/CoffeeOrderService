package com.justin.teaorderservice.order;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Order> findAll(){
        return new ArrayList<>(store.values());
    }

    public void update(Long orderId, Order updateParam){
        Order findOrder = findById(orderId);
        findOrder.setTeaOrderList(updateParam.getTeaOrderList());
    }

    public void clearStore(){
        store.clear();
    }
}
