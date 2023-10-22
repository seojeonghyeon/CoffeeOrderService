package com.justin.teaorderservice.modules.point.form;

import com.justin.teaorderservice.modules.point.Point;
import com.justin.teaorderservice.modules.point.PointStatus;
import com.justin.teaorderservice.modules.point.response.ResponsePointResult;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointResultForm {
    @NotNull
    private String memberName;
    @NotNull
    private PointStatus status;
    @NotNull
    private Integer currentPoint;
    @NotNull
    private Integer addPoint;

    public static PointResultForm createPointResultForm(Point point){
        PointResultForm pointResultForm = PointResultForm.builder()
                .memberName(point.getMember().getMemberName())
                .status(point.getStatus())
                .currentPoint(point.getCurrentPoint())
                .addPoint(point.getAddPoint())
                .build();
        return pointResultForm;
    }
}
