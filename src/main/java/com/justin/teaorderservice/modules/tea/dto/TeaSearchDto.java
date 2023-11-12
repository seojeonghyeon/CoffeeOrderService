package com.justin.teaorderservice.modules.tea.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class TeaSearchDto {

    private Long id;
    private String teaName;
    private Integer price;
    private String teaImage;
    private Boolean disabled;

    @QueryProjection
    public TeaSearchDto(Long id, String teaName, Integer price, String teaImage, Boolean disabled) {
        this.id = id;
        this.teaName = teaName;
        this.price = price;
        this.teaImage = teaImage;
        this.disabled = disabled;
    }
}
