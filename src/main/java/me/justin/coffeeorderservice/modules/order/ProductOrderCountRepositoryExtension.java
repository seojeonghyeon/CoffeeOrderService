package me.justin.coffeeorderservice.modules.order;

import java.time.LocalDate;

public interface ProductOrderCountRepositoryExtension {
    ProductOrderCount findByProductIdAndOrderDate(Long productId, LocalDate orderDate);
}
