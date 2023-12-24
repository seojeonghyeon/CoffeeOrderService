package me.justin.coffeeorderservice.modules.order;

import java.time.LocalDate;

public interface ProductOrderCountRepositoryCustom {
    ProductOrderCount findByProductIdAndOrderDate(Long productId, LocalDate orderDate);
}
