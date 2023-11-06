package com.justin.teaorderservice.modules.tea.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.tea.Tea;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseTeaListItem {

    @Schema(description = "Tea 고유 식별 번호", nullable = false, example = "1")
    private Long id;

    @Schema(description = "Tea 이름", nullable = false, example = "Americano(Hot)")
    private String teaName;

    @Schema(description = "Tea 가격", nullable = false, example = "2000")
    private Integer price;


    public static ResponseTeaListItem createResponseTeaListItem(Tea tea){
        ResponseTeaListItem responseTeaListItem = ResponseTeaListItem.builder()
                .id(tea.getId())
                .teaName(tea.getTeaName())
                .price(tea.getPrice())
                .build();
        return responseTeaListItem;
    }
}
