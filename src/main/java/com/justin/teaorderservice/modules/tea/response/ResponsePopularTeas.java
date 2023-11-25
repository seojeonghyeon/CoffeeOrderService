package com.justin.teaorderservice.modules.tea.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.tea.dto.PopularTeaDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponsePopularTeas {
    List<ResponsePopularTea> responsePopularTeaList;

    public static ResponsePopularTeas createResponsePopularTeas(List<PopularTeaDto> popularTeaDtos) {
        ResponsePopularTeas responsePopularTeas = ResponsePopularTeas.builder()
                .responsePopularTeaList(new ArrayList<>())
                .build();
        popularTeaDtos.forEach(popularTea -> {
            ResponsePopularTea responsePopularTea = ResponsePopularTea.builder()
                            .id(popularTea.getId())
                            .teaName(popularTea.getTeaName())
                            .teaImage(popularTea.getTeaImage())
                            .price(popularTea.getPrice())
                            .stockQuantity(popularTea.getStockQuantity())
                            .orderCount(popularTea.getOrderCount())
                            .build();
            responsePopularTeas.responsePopularTeaList.add(responsePopularTea);
        });
        return responsePopularTeas;
    }
}
