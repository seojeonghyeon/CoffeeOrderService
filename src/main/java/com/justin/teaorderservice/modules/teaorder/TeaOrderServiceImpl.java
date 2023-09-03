package com.justin.teaorderservice.modules.teaorder;

import com.justin.teaorderservice.modules.tea.Tea;
import com.justin.teaorderservice.modules.tea.TeaService;
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
    private final TeaService teaService;

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

    @Transactional
    @Override
    public void update(String userId, TeaOrder teaOrder) {
        /**
         * 사용자 Point 감소
         */

        Tea tea = teaService.findById(teaOrder.getTeaId());
        Integer remaining = tea.getQuantity() - teaOrder.getOrderQuantity();
        if(remaining >= 0){
            teaOrder.updateQuantity(remaining);
            tea.setQuantity(remaining);
            teaService.update(tea.getId(), tea);
        }
    }
}