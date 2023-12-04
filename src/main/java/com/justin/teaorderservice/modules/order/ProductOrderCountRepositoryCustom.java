package com.justin.teaorderservice.modules.order;

import java.time.LocalDate;

public interface ProductOrderCountRepositoryCustom {
    ProductOrderCount findByProductIdAndOrderDate(Long productId, LocalDate orderDate);
}
