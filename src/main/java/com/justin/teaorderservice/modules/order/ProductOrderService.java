package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.modules.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final ProductRepository productRepository;
    private final ProductCountRepository productCountRepository;

    public List<ProductOrder> findByOrderId(Long orderId) {
        return productOrderRepository.findByOrderId(orderId);
    }

    public ProductOrder findById(Long id) {
        return productOrderRepository.findById(id).orElse(null);
    }

    @Transactional
    public ProductOrder save(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    @Transactional
    public ProductOrder drinkOrder(Long drinkId, Integer orderPrice, Integer orderQuantity){
        Product product = productRepository.findById(drinkId).orElse(null);
        ProductCount productCount = productCountRepository.findByProductIdAndOrderDate(drinkId, LocalDate.now());
        return ProductOrder.createProductOrder(product, productCount, orderPrice, orderQuantity);
    }
}
