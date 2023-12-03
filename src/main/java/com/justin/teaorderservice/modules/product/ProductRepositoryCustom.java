package com.justin.teaorderservice.modules.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    Page<ProductSearchDto> search(ProductSearchCondition productSearchCondition, Pageable pageable);
}
