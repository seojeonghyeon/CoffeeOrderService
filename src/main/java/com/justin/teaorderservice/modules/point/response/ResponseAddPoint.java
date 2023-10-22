package com.justin.teaorderservice.modules.point.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseAddPoint {
    @Schema(description = "기존 Point 정보", nullable = false, example = "1000")
    Integer point;
    @Schema(description = "추가할 Point 정보", nullable = false, example = "2000")
    Integer addPoint;

    public static ResponseAddPoint createResponseAddPoint(Integer point, Integer addPoint){
        ResponseAddPoint responseAddPoint = ResponseAddPoint.builder()
                .point(point)
                .addPoint(addPoint)
                .build();
        return responseAddPoint;
    }
}
