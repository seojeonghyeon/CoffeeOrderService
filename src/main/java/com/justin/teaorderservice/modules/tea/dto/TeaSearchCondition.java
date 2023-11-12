package com.justin.teaorderservice.modules.tea.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Data
public class TeaSearchCondition {
    private String teaName;
    private Integer minPrice;
    private Integer maxPrice;
    private Boolean disabled;
}
