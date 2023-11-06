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

    @Schema(description = "주문 식별 번호", nullable = true, example = "1")
    private Long id;

    @Schema(description = "사용자 이름", nullable = false, example = "화려한 고등어")
    private String userName;

    @Schema(description = "사용자 주문 정보", nullable = false)
    private List<ResponseTeaOrder> teaOrderList;

    public static ResponseOrder createResponseOrder(Long orderId, String userName, List<ResponseTeaOrder> responseTeaOrderList){
        ResponseOrder responseOrder = ResponseOrder.builder()
                .id(orderId)
                .userName(userName)
                .teaOrderList(responseTeaOrderList)
                .build();
        return responseOrder;
    }
    public static ResponseOrder createResponseOrder(String userName, List<ResponseTeaOrder> responseTeaOrderList){
        ResponseOrder responseOrder = ResponseOrder.builder()
                .userName(userName)
                .teaOrderList(responseTeaOrderList)
                .build();
        return responseOrder;
    }
}
