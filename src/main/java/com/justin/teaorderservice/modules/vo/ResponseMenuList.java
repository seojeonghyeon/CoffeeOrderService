package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseMenuList {
    @Schema(description = "Menu 리스트", nullable = false)
    private List<ResponseMenuListItem> responseMenuListItems;

    public static ResponseMenuList createResponseMenuList(List<ResponseMenuListItem> responseMenuListItems){
        ResponseMenuList responseMenuList = ResponseMenuList
                .builder()
                .responseMenuListItems(responseMenuListItems)
                .build();
        return responseMenuList;
    }
}
