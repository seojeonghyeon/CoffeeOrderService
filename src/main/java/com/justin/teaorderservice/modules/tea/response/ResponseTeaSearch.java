package com.justin.teaorderservice.modules.tea.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.tea.dto.TeaSearchDto;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseTeaSearch {
    private Long id;
    private String teaName;
    private Integer price;
    private String teaImage;
    private Boolean disabled;

    public static ResponseTeaSearch createResponseTeaSearch(TeaSearchDto teaSearchDto){
        ResponseTeaSearch responseTeaSearch = ResponseTeaSearch.builder()
                .id(teaSearchDto.getId())
                .teaName(teaSearchDto.getTeaName())
                .teaImage(teaSearchDto.getTeaImage())
                .price(teaSearchDto.getPrice())
                .disabled(teaSearchDto.getDisabled())
                .build();
        return responseTeaSearch;
    }
}
