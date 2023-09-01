package com.justin.teaorderservice.modules.teaorder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.*;

@Slf4j
@Repository
public class TeaOrderRepository {
    private static final Map<Long, TeaOrder> store = new HashMap<>();
    private static long sequence = 0L;

    public TeaOrder save(TeaOrder teaOrder) {
        teaOrder.setId(++sequence);
        log.info("save: member={}", teaOrder);
        store.put(teaOrder.getId(), teaOrder);
        return teaOrder;
    }

    public TeaOrder findById(Long id) {
        return store.get(id);
    }

    public List<TeaOrder> findByOrderId(Long orderId) {
        return findAll().stream()
                .filter(teaOrder -> teaOrder.getOrderId().equals(orderId))
                .toList();
    }

    public List<TeaOrder> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
