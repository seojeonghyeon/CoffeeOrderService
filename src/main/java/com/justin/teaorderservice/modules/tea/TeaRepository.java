package com.justin.teaorderservice.modules.tea;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeaRepository {
    private static final Map<Long, Tea> store = new HashMap<>();
    private static long sequence = 0L;

    public Tea save(Tea tea) {
        tea.setId(++sequence);
        store.put(tea.getId(), tea);
        return tea;
    }

    public Tea findById(Long id) {
        return store.get(id);
    }

    public List<Tea> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long teaId, Tea updateParam) {
        Tea findTea = findById(teaId);
        findTea.setTeaName(updateParam.getTeaName());
        findTea.setPrice(updateParam.getPrice());
        findTea.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }

}
