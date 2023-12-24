package me.justin.coffeeorderservice.modules.order;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class ProductOrderCountDto {
    private Long menuId;
    private Integer countUp;

    @QueryProjection
    public ProductOrderCountDto(Long menuId, Integer countUp) {
        this.menuId = menuId;
        this.countUp = countUp;
    }
}
