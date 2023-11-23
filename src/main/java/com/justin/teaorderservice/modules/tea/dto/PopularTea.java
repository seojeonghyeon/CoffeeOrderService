package com.justin.teaorderservice.modules.tea.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class PopularTea {

    private Long id;
    private String teaName;
    private Integer price;
    private Integer stockQuantity;
    private String teaImage;
    private Integer orderCount;

    @QueryProjection
    public PopularTea(Long id, String teaName, Integer price, Integer stockQuantity, String teaImage) {
        this.id = id;
        this.teaName = teaName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.teaImage = teaImage;
    }

    public void setCount(Integer orderCount){
        this.orderCount = orderCount;
    }

}