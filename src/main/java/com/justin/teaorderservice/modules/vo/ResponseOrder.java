package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private List<ResponseProductOrder> productOrderList;

    public static ResponseOrder createResponseOrder(Long orderId, String userName, List<ResponseProductOrder> responseProductOrderList){
        ResponseOrder responseOrder = ResponseOrder.builder()
                .id(orderId)
                .userName(userName)
                .productOrderList(responseProductOrderList)
                .build();
        return responseOrder;
    }
    public static ResponseOrder createResponseOrder(String userName, List<ResponseProductOrder> responseProductOrderList){
        ResponseOrder responseOrder = ResponseOrder.builder()
                .userName(userName)
                .productOrderList(responseProductOrderList)
                .build();
        return responseOrder;
    }
}
