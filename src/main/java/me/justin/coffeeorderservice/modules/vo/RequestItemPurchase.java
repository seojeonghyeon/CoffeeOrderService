package me.justin.coffeeorderservice.modules.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestItemPurchase {

    @Schema(description = "사용자 주문 정보", nullable = false)
    @NotNull
    @Valid
    private List<RequestItemOrder> requestItemOrderList;

    public static RequestItemPurchase createRequestItemPurchase(RequestItemOrder... requestItemOrders){
        RequestItemPurchase requestItemPurchase = RequestItemPurchase.builder()
                .requestItemOrderList(new ArrayList<>())
                .build();
        requestItemPurchase.requestItemOrderList.addAll(Arrays.asList(requestItemOrders));
        return requestItemPurchase;
    }
}
