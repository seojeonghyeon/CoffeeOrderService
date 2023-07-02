package com.justin.teaorderservice.order;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeaRepository {
    private static final Map<Long, Tea> store = new HashMap<>();
    private static long sequence = 0L;

    public Tea save(Tea Tea) {
        Tea.setId(++sequence);
        store.put(Tea.getId(), Tea);
        return Tea;
    }

    public Tea findById(Long id) {
        return store.get(id);
    }

    public List<Tea> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long TeaId, Tea updateParam) {
        Tea findTea = findById(TeaId);
        findTea.setTeaName(updateParam.getTeaName());
        findTea.setPrice(updateParam.getPrice());
        findTea.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }

}
