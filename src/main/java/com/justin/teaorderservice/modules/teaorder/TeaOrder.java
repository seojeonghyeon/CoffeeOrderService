package com.justin.teaorderservice.modules.teaorder;

import com.justin.teaorderservice.modules.order.Order;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table
@Getter
@Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class TeaOrder {

    @Id @GeneratedValue
    private Long id;

    private Order order;

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
