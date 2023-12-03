package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.product.Product;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseProduct {
    private Long id;
    private String productName;
    private Integer price;
    private Integer quantity;
    private String productImage;
    private String description;

    public static ResponseProduct createResponseProduct(Product product){
        ResponseProduct responseProduct = ResponseProduct.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .quantity(product.getStockQuantity())
                .productImage(product.getProductImage())
                .description(product.getDescription())
                .build();
        return responseProduct;
    }
}
