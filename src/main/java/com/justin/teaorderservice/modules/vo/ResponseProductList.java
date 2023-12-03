package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseProductList {
    @Schema(description = "Product 리스트", nullable = false)
    private List<ResponseProductListItem> responseProductListItems;

    public static ResponseProductList createResponseProductList(List<ResponseProductListItem> responseProductListItems){
        ResponseProductList responseProductList = ResponseProductList
                .builder()
                .responseProductListItems(responseProductListItems)
                .build();
        return responseProductList;
    }
}
