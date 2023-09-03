package com.justin.teaorderservice.modules.teaorder.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestItemOrder {

    @Schema(description = "Tea 고유 식별 번호", nullable = false, example = "1")
    @NotNull
    private Long id;

    @Schema(description = "Tea 이름", nullable = false, example = "Americano(Hot)")
    @NotNull
    private String teaName;

    @Schema(description = "Tea 가격", nullable = false, example = "2000")
    @NotNull
    @Range(min = 0, max = 1000000)
    private Integer price;

    @Schema(description = "Tea 재고", nullable = false, example = "20")
    @NotNull
    @Range(min = 0)
    private Integer quantity;

    @Schema(description = "Tea 주문 수량", nullable = false, example = "1")
    @NotNull
    @Range(min = 0, max = 1000)
    private Integer orderQuantity;
}
