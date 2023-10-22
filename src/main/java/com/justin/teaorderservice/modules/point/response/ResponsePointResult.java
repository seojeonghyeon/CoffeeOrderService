package com.justin.teaorderservice.modules.point.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.point.Point;
import com.justin.teaorderservice.modules.point.PointStatus;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponsePointResult {
    private String memberName;
    private PointStatus status;
    private Integer currentPoint;
    private Integer addPoint;

    public static ResponsePointResult createResponsePointResult(Point point){
        ResponsePointResult responsePointResult = ResponsePointResult.builder()
                .memberName(point.getMember().getMemberName())
                .status(point.getStatus())
                .currentPoint(point.getCurrentPoint())
                .addPoint(point.getAddPoint())
                .build();
        return responsePointResult;
    }
}
