package com.justin.teaorderservice.modules.tea.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseTeaList {
    @Schema(description = "Tea 리스트", nullable = false)
    private List<ResponseTeaListItem> responseTeaListItems;

    public static ResponseTeaList createResponseTeaList(List<ResponseTeaListItem> responseTeaListItems){
        ResponseTeaList responseTeaList = ResponseTeaList
                .builder()
                .responseTeaListItems(responseTeaListItems)
                .build();
        return responseTeaList;
    }
}
