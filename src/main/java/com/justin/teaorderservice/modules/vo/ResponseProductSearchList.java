package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseProductSearchList {
    List<ResponseProductSearch> responseProductSearchList;

    public static ResponseProductSearchList createResponseProductSearchList(List<ResponseProductSearch> responseProductSearchList){
        ResponseProductSearchList productSearchList = ResponseProductSearchList.builder()
                .responseProductSearchList(responseProductSearchList)
                .build();
        return productSearchList;
    }
}
