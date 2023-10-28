package com.justin.teaorderservice.modules.order.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseItemPurchase {

    @Schema(description = "사용자 이름", nullable = false, example = "유쾌한 고등어")
    private String userId;

    @Schema(description = "사용자 주문 정보", nullable = false)
    private List<ResponseItemOrder> itemOrderFormList;
}
