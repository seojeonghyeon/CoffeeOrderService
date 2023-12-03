package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseProductListItem {

    @Schema(description = "Product 고유 식별 번호", nullable = false, example = "1")
    private Long id;

    @Schema(description = "Product 이름", nullable = false, example = "Americano(Hot)")
    private String productName;

    @Schema(description = "Product 가격", nullable = false, example = "2000")
    private Integer price;


    public static ResponseProductListItem createResponseProductListItem(Product product){
        ResponseProductListItem responseProductListItem = ResponseProductListItem.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .build();
        return responseProductListItem;
    }
}
