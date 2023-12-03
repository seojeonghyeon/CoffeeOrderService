package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.product.PopularProductDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponsePopularProducts {
    List<ResponsePopularProduct> responsePopularProductList;

    public static ResponsePopularProducts createResponsePopularProducts(List<PopularProductDto> popularProductDtos) {
        ResponsePopularProducts responsePopularProducts = ResponsePopularProducts.builder()
                .responsePopularProductList(new ArrayList<>())
                .build();
        popularProductDtos.forEach(popularProduct -> {
            ResponsePopularProduct responsePopularProduct = ResponsePopularProduct.builder()
                            .id(popularProduct.getId())
                            .teaName(popularProduct.getDrinkName())
                            .teaImage(popularProduct.getDrinkImage())
                            .price(popularProduct.getPrice())
                            .stockQuantity(popularProduct.getStockQuantity())
                            .orderCount(popularProduct.getOrderCount())
                            .build();
            responsePopularProducts.responsePopularProductList.add(responsePopularProduct);
        });
        return responsePopularProducts;
    }
}
