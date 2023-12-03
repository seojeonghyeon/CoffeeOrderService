package com.justin.teaorderservice.modules.product;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class ProductSearchDto {

    private Long id;
    private String drinkName;
    private Integer price;
    private String drinkImage;
    private Boolean disabled;

    @QueryProjection
    public ProductSearchDto(Long id, String drinkName, Integer price, String drinkImage, Boolean disabled) {
        this.id = id;
        this.drinkName = drinkName;
        this.price = price;
        this.drinkImage = drinkImage;
        this.disabled = disabled;
    }
}
