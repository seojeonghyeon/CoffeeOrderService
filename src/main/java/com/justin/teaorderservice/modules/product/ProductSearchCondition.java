package com.justin.teaorderservice.modules.product;

import lombok.*;

@Data
public class ProductSearchCondition {
    private String productName;
    private Integer minPrice;
    private Integer maxPrice;
    private Boolean disabled;
}
