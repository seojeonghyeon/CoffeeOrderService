package com.justin.teaorderservice.modules.menu;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
public class MenuSearchDto {

    private Long id;
    private String menuName;
    private Integer price;
    private String menuImage;
    private Boolean disabled;

    @QueryProjection
    public MenuSearchDto(Long id, String menuName, Integer price, String menuImage, Boolean disabled) {
        this.id = id;
        this.menuName = menuName;
        this.price = price;
        this.menuImage = menuImage;
        this.disabled = disabled;
    }
}
