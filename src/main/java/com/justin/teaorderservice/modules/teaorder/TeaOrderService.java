package com.justin.teaorderservice.modules.teaorder;

import com.justin.teaorderservice.modules.tea.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeaOrderService{

    private final TeaOrderRepository teaOrderRepository;
    private final TeaRepository teaRepository;
    private final TeaOrderCountRepository teaOrderCountRepository;

    public List<TeaOrder> findByOrderId(Long orderId) {
        return teaOrderRepository.findByOrderId(orderId);
    }

    public TeaOrder findById(Long id) {
        return teaOrderRepository.findById(id).orElse(null);
    }

    @Transactional
    public TeaOrder save(TeaOrder teaOrder) {
        return teaOrderRepository.save(teaOrder);
    }

    @Transactional
    public TeaOrder teaOrder(Long teaId, Integer orderPrice, Integer orderQuantity){
        Tea tea = teaRepository.findById(teaId).orElse(null);
        TeaOrderCount teaOrderCount = teaOrderCountRepository
                .findByTeaId(teaId)
                .orElse(TeaOrderCount.createTeaOrderCount(tea));
        return TeaOrder.createTeaOrder(tea, teaOrderCount, orderPrice, orderQuantity);
    }
}
