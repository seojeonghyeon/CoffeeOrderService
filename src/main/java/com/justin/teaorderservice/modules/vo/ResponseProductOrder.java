package com.justin.teaorderservice.modules.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.justin.teaorderservice.modules.menu.Menu;
import com.justin.teaorderservice.modules.order.ProductOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL) @AllArgsConstructor
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseProductOrder {

    @Schema(description = "Menu 고유 식별 번호", nullable = false, example = "1")
    private Long id;

    @Schema(description = "Menu 이름", nullable = false, example = "Americano(Hot)")
    private String menuName;

    @Schema(description = "Menu 가격", nullable = false, example = "2000")
    private Integer price;

    @Schema(description = "Menu 재고", nullable = false, example = "20")
    private Integer quantity;

    @Schema(description = "Menu 주문 수량", nullable = false, example = "1")
    private Integer orderQuantity;

    public static ResponseProductOrder createResponseProductOrder(ProductOrder productOrder){
        ResponseProductOrder responseProductOrder = ResponseProductOrder.builder()
                .id(productOrder.getMenu().getId())
                .orderQuantity(productOrder.getQuantity())
                .price(productOrder.getOrderPrice())
                .quantity(productOrder.getQuantity())
                .menuName(productOrder.getMenu().getMenuName())
                .build();
        return responseProductOrder;
    }
    public static ResponseProductOrder createResponseProductOrder(Menu menu){
        ResponseProductOrder responseProductOrder = ResponseProductOrder.builder()
                .id(menu.getId())
                .orderQuantity(0)
                .price(menu.getPrice())
                .quantity(menu.getStockQuantity())
                .menuName(menu.getMenuName())
                .build();
        return responseProductOrder;
    }
}
