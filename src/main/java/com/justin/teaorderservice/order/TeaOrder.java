package com.justin.teaorderservice.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeaOrder {
    private Long id;
    private String teaName;
    private Integer price;
    private Integer quantity;
    private Integer orderQuantity;
}
