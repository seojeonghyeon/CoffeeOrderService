package com.justin.teaorderservice.modules.teaorder;

import lombok.*;

@Getter
@Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class TeaOrder {
    private Long id;
    private Long orderId;
    private Long teaId;
    private String teaName;
    private Integer price;
    private Integer quantity;
    private Integer orderQuantity;

    @Builder.Default
    private Boolean disabled = true;

    public void updateQuantity(Integer quantity){
        this.quantity = quantity;
        this.disabled = false;
    }
}
