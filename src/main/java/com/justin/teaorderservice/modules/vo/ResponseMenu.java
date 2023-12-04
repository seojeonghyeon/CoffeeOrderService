package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.menu.Menu;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseMenu {
    private Long id;
    private String menuName;
    private Integer price;
    private Integer quantity;
    private String menuImage;
    private String description;

    public static ResponseMenu createResponseMenu(Menu menu){
        ResponseMenu responseMenu = ResponseMenu.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .quantity(menu.getStockQuantity())
                .menuImage(menu.getMenuImage())
                .description(menu.getDescription())
                .build();
        return responseMenu;
    }
}
