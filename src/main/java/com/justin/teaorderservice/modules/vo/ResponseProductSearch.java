package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.product.ProductSearchDto;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseProductSearch {
    private Long id;
    private String productName;
    private Integer price;
    private String productImage;
    private Boolean disabled;

    public static ResponseProductSearch createResponseProductSearch(ProductSearchDto productSearchDto){
        ResponseProductSearch responseProductSearch = ResponseProductSearch.builder()
                .id(productSearchDto.getId())
                .productName(productSearchDto.getDrinkName())
                .productImage(productSearchDto.getDrinkImage())
                .price(productSearchDto.getPrice())
                .disabled(productSearchDto.getDisabled())
                .build();
        return responseProductSearch;
    }
}
