package com.justin.teaorderservice.modules.order;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class ProductCountDto {
    private Long productId;
    private Integer countUp;

    @QueryProjection
    public ProductCountDto(Long productId, Integer countUp) {
        this.productId = productId;
        this.countUp = countUp;
    }
}
