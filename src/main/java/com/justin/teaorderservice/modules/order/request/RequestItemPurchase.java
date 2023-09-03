package com.justin.teaorderservice.modules.order.request;

import com.justin.teaorderservice.modules.teaorder.request.RequestItemOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestItemPurchase {

    @Schema(description = "사용자 고유 식별 번호", nullable = true, example = "1")
    @Null
    private String id;

    @Schema(description = "사용자 고유 식별자", nullable = false, example = "17036164-7977-462e-acde-eee4cf822f64")
    @NotBlank
    private String userId;

    @Schema(description = "사용자 주문 정보", nullable = false)
    @NotNull
    @Valid
    private List<RequestItemOrder> requestItemOrderList;
}
