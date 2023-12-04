package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.menu.MenuSearchDto;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseMenuSearch {
    private Long id;
    private String menuName;
    private Integer price;
    private String menuImage;
    private Boolean disabled;

    public static ResponseMenuSearch createResponseMenuSearch(MenuSearchDto menuSearchDto){
        ResponseMenuSearch responseMenuSearch = ResponseMenuSearch.builder()
                .id(menuSearchDto.getId())
                .menuName(menuSearchDto.getMenuName())
                .menuImage(menuSearchDto.getMenuImage())
                .price(menuSearchDto.getPrice())
                .disabled(menuSearchDto.getDisabled())
                .build();
        return responseMenuSearch;
    }
}
