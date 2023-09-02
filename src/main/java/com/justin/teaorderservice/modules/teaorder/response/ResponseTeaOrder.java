package com.justin.teaorderservice.modules.teaorder.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseTeaOrder {

    @Schema(description = "Tea 고유 식별 번호", nullable = false, example = "1")
    private Long id;

    @Schema(description = "Tea 이름", nullable = false, example = "Americano(Hot)")
    private String teaName;

    @Schema(description = "Tea 가격", nullable = false, example = "2000")
    private Integer price;

    @Schema(description = "Tea 재고", nullable = false, example = "20")
    private Integer quantity;

    @Schema(description = "Tea 주문 수량", nullable = false, example = "1")
    private Integer orderQuantity;
}
