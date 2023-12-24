package me.justin.coffeeorderservice.modules.order;

import me.justin.coffeeorderservice.modules.menu.*;
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
    private final MenuRepository menuRepository;
    private final ProductOrderCountRepository productOrderCountRepository;

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
        Menu menu = menuRepository.findById(drinkId).orElse(null);
        ProductOrderCount productOrderCount = productOrderCountRepository.findByProductIdAndOrderDate(drinkId, LocalDate.now());
        return ProductOrder.createProductOrder(menu, productOrderCount, orderPrice, orderQuantity);
    }
}
