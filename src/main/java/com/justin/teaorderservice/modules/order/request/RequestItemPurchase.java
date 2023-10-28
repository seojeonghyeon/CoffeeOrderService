package com.justin.teaorderservice.modules.order.request;

import com.justin.teaorderservice.modules.teaorder.request.RequestItemOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RequestItemPurchase {

    @Schema(description = "사용자 주문 정보", nullable = false)
    @NotNull
    @Valid
    private List<RequestItemOrder> requestItemOrderList;
}
