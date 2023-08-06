package com.justin.teaorderservice.modules.order.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseItemOrder {
    private Long id;
    private String teaName;
    private Integer price;
    private Integer quantity;
    private Integer orderQuantity;
}