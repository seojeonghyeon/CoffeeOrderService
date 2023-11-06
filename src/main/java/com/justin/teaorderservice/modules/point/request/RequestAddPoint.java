package com.justin.teaorderservice.modules.point.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestAddPoint {
    @Schema(description = "현재 사용자 Point", nullable = false)
    @NotNull
    private Integer point;

    @Schema(description = "추가 할 Point", nullable = false)
    @NotNull
    private Integer addPoint;

    public static RequestAddPoint createRequestAddPoint(Integer point, Integer addPoint){
        RequestAddPoint requestAddPoint = RequestAddPoint.builder()
                .point(point)
                .addPoint(addPoint)
                .build();
        return requestAddPoint;
    }
}
