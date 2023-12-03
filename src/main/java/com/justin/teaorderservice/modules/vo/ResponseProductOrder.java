package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.product.Product;
import com.justin.teaorderservice.modules.order.ProductOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL) @AllArgsConstructor
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseProductOrder {

    @Schema(description = "Product 고유 식별 번호", nullable = false, example = "1")
    private Long id;

    @Schema(description = "Product 이름", nullable = false, example = "Americano(Hot)")
    private String productName;

    @Schema(description = "Product 가격", nullable = false, example = "2000")
    private Integer price;

    @Schema(description = "Product 재고", nullable = false, example = "20")
    private Integer quantity;

    @Schema(description = "Product 주문 수량", nullable = false, example = "1")
    private Integer orderQuantity;

    public static ResponseProductOrder createResponseProductOrder(ProductOrder productOrder){
        ResponseProductOrder responseProductOrder = ResponseProductOrder.builder()
                .id(productOrder.getProduct().getId())
                .orderQuantity(productOrder.getQuantity())
                .price(productOrder.getOrderPrice())
                .quantity(productOrder.getQuantity())
                .productName(productOrder.getProduct().getProductName())
                .build();
        return responseProductOrder;
    }
    public static ResponseProductOrder createResponseProductOrder(Product product){
        ResponseProductOrder responseProductOrder = ResponseProductOrder.builder()
                .id(product.getId())
                .orderQuantity(0)
                .price(product.getPrice())
                .quantity(product.getStockQuantity())
                .productName(product.getProductName())
                .build();
        return responseProductOrder;
    }
}
