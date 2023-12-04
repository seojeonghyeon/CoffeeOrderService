package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.menu.PopularMenuDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponsePopularMenus {
    List<ResponsePopularMenu> responsePopularMenuList;

    public static ResponsePopularMenus createResponsePopularMenus(List<PopularMenuDto> popularMenuDtos) {
        ResponsePopularMenus responsePopularMenus = ResponsePopularMenus.builder()
                .responsePopularMenuList(new ArrayList<>())
                .build();
        popularMenuDtos.forEach(popularMenu -> {
            ResponsePopularMenu responsePopularMenu = ResponsePopularMenu.builder()
                            .id(popularMenu.getId())
                            .menuName(popularMenu.getMenuName())
                            .menuImage(popularMenu.getMenuImage())
                            .price(popularMenu.getPrice())
                            .stockQuantity(popularMenu.getStockQuantity())
                            .orderCount(popularMenu.getOrderCount())
                            .build();
            responsePopularMenus.responsePopularMenuList.add(responsePopularMenu);
        });
        return responsePopularMenus;
    }
}
