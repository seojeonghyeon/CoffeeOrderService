package com.justin.teaorderservice.modules.teaorder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TeaOrderServiceImpl implements TeaOrderService{

    private final TeaOrderRepository teaOrderRepository;

    @Override
    public List<TeaOrder> findByOrderId(Long orderId) {
        return teaOrderRepository.findByOrderId(orderId);
    }

    @Override
    public TeaOrder findById(Long id) {
        return teaOrderRepository.findById(id);
    }

    @Override
    public TeaOrder save(TeaOrder teaOrder) {
        return teaOrderRepository.save(teaOrder);
    }
}
