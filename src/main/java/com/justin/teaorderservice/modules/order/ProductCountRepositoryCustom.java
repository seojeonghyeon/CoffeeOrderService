package com.justin.teaorderservice.modules.order;

import java.time.LocalDate;

public interface ProductCountRepositoryCustom {
    ProductCount findByProductIdAndOrderDate(Long productId, LocalDate orderDate);
}
