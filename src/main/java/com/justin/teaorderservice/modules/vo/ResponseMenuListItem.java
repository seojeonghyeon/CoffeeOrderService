package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.menu.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseMenuListItem {

    @Schema(description = "Menu 고유 식별 번호", nullable = false, example = "1")
    private Long id;

    @Schema(description = "Menu 이름", nullable = false, example = "Americano(Hot)")
    private String menuName;

    @Schema(description = "Menu 가격", nullable = false, example = "2000")
    private Integer price;


    public static ResponseMenuListItem createResponseMenuListItem(Menu menu){
        ResponseMenuListItem responseMenuListItem = ResponseMenuListItem.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .build();
        return responseMenuListItem;
    }
}
