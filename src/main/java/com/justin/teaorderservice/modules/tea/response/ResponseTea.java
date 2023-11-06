package com.justin.teaorderservice.modules.tea.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.tea.Tea;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseTea {
    private Long id;
    private String teaName;
    private Integer price;
    private Integer quantity;
    private String teaImage;
    private String description;

    public static ResponseTea createResponseTea(Tea tea){
        ResponseTea responseTea = ResponseTea.builder()
                .id(tea.getId())
                .teaName(tea.getTeaName())
                .price(tea.getPrice())
                .quantity(tea.getStockQuantity())
                .teaImage(tea.getTeaImage())
                .description(tea.getDescription())
                .build();
        return responseTea;
    }
}
