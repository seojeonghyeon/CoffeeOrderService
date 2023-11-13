package com.justin.teaorderservice.modules.tea.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseTeaSearchList {
    List<ResponseTeaSearch> responseTeaSearchList;

    public static ResponseTeaSearchList createResponseTeaSearchList(List<ResponseTeaSearch> responseTeaSearchList){
        ResponseTeaSearchList teaSearchList = ResponseTeaSearchList.builder()
                .responseTeaSearchList(responseTeaSearchList)
                .build();
        return teaSearchList;
    }
}
