package com.justin.teaorderservice.modules.ordercount;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class OrderCountDto {
    private Long teaId;
    private Integer countUp;

    @QueryProjection
    public OrderCountDto(Long teaId, Integer countUp) {
        this.teaId = teaId;
        this.countUp = countUp;
    }
}
