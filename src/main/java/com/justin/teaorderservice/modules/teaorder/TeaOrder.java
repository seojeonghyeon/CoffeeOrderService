package com.justin.teaorderservice.modules.teaorder;

import com.justin.teaorderservice.modules.order.Order;
import com.justin.teaorderservice.modules.tea.Tea;
import jakarta.persistence.*;
import lombok.*;
import static jakarta.persistence.FetchType.*;

@Table
@Getter
@Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class TeaOrder {

    @Id @GeneratedValue
    @Column(name = "tea_order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    private Tea tea;

    private Integer orderPrice;
    private Integer quantity;

    @Builder.Default
    private Boolean disabled = true;

    public Integer getTotalPrice(){
        return orderPrice * quantity;
    }
}
