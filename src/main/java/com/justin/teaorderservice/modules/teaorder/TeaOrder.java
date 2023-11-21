package com.justin.teaorderservice.modules.teaorder;

import com.justin.teaorderservice.modules.common.BaseEntity;
import com.justin.teaorderservice.modules.order.Order;
import com.justin.teaorderservice.modules.ordercount.OrderCount;
import com.justin.teaorderservice.modules.tea.Tea;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
@Builder @NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TeaOrder extends BaseEntity {

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_count_id")
    private OrderCount orderCount;

    public static TeaOrder createTeaOrder(Tea tea, OrderCount orderCount, Integer orderPrice, Integer quantity){
        TeaOrder teaOrder = TeaOrder.builder()
                .orderPrice(orderPrice)
                .quantity(quantity)
                .disabled(false)
                .build();
        tea.addTeaOrder(teaOrder);
        orderCount.addTeaOrder(teaOrder);
        tea.removeStock(quantity);
        orderCount.order(quantity);
        return teaOrder;
    }

    public void cancel(){
        setDisabled(true);
        getTea().addStock(quantity);
        orderCount.cancel(quantity);
    }

    public Integer getTotalPrice(){
        return orderPrice * quantity;
    }
}
