package com.justin.teaorderservice.modules.teaorder;

import com.justin.teaorderservice.modules.order.Order;
import com.justin.teaorderservice.modules.tea.Tea;
import jakarta.persistence.*;
import lombok.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TeaOrder {

    @Id @GeneratedValue
    @Column(name = "tea_order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tea_id")
    private Tea tea;

    private Integer orderPrice;
    private Integer quantity;

    private Boolean disabled;

    public static TeaOrder createTeaOrder(Tea tea, Integer orderPrice, Integer quantity){
        TeaOrder teaOrder = TeaOrder.builder()
                .tea(tea)
                .orderPrice(orderPrice)
                .quantity(quantity)
                .disabled(false)
                .build();
        tea.removeStock(quantity);
        return teaOrder;
    }

    public void cancel(){
        setDisabled(true);
        getTea().addStock(quantity);
    }

    public Integer getTotalPrice(){
        return orderPrice * quantity;
    }
}
