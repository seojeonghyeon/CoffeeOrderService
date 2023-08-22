package com.justin.teaorderservice.modules.order.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.tea.response.ResponseTeaOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseOrder {

    @Schema(description = "사용자 고유 식별 번호", nullable = true, example = "1")
    private Long id;

    @Schema(description = "사용자 고유 식별자", nullable = false, example = "17036164-7977-462e-acde-eee4cf822f64")
    private String userId;

    @Schema(description = "사용자 주문 정보", nullable = false)
    private List<ResponseTeaOrder> teaOrderList;
}
