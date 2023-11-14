package com.justin.teaorderservice.modules.teaorder;

import com.justin.teaorderservice.modules.order.Order;
import com.justin.teaorderservice.modules.tea.Tea;
import com.justin.teaorderservice.modules.tea.TeaOrderCount;
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

    @Transient
    private TeaOrderCount teaOrderCount;

    public static TeaOrder createTeaOrder(Tea tea, TeaOrderCount teaOrderCount, Integer orderPrice, Integer quantity){
        TeaOrder teaOrder = TeaOrder.builder()
                .tea(tea)
                .orderPrice(orderPrice)
                .quantity(quantity)
                .disabled(false)
                .teaOrderCount(teaOrderCount)
                .build();
        tea.removeStock(quantity);
        teaOrderCount.order(quantity);

        return teaOrder;
    }

    public void cancel(){
        setDisabled(true);
        getTea().addStock(quantity);
        teaOrderCount.cancel(quantity);
    }

    public Integer getTotalPrice(){
        return orderPrice * quantity;
    }
}
