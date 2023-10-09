package com.justin.teaorderservice.modules.order.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.teaorder.response.ResponseTeaOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) @AllArgsConstructor
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseOrder {

    @Schema(description = "사용자 고유 식별 번호", nullable = true, example = "1")
    private Long id;

    @Schema(description = "사용자 고유 식별자", nullable = false, example = "17036164-7977-462e-acde-eee4cf822f64")
    private String userId;

    @Schema(description = "사용자 주문 정보", nullable = false)
    private List<ResponseTeaOrder> teaOrderList;

    public static ResponseOrder createResponseOrder(Long orderId, String userName, List<ResponseTeaOrder> responseTeaOrderList){
        ResponseOrder responseOrder = ResponseOrder.builder()
                .id(orderId)
                .userId(userName)
                .teaOrderList(responseTeaOrderList)
                .build();
        return responseOrder;
    }
    public static ResponseOrder createResponseOrder(String userName, List<ResponseTeaOrder> responseTeaOrderList){
        ResponseOrder responseOrder = ResponseOrder.builder()
                .userId(userName)
                .teaOrderList(responseTeaOrderList)
                .build();
        return responseOrder;
    }
}
