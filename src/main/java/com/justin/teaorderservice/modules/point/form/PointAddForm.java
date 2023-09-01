package com.justin.teaorderservice.modules.point.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointAddForm {

    @NumberFormat(pattern = "###,###")
    @NotNull
    @PositiveOrZero
    @Range(min = 0, max = 1000000)
    private Integer point;

    @NumberFormat(pattern = "###,###")
    @NotNull
    @PositiveOrZero
    @Range(min = 0, max = 1000000)
    private Integer addPoint;
}
