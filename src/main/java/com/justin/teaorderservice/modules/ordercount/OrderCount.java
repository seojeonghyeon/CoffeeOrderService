package com.justin.teaorderservice.modules.ordercount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justin.teaorderservice.modules.tea.Tea;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_counts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderCount {

    @Id @GeneratedValue
    @Column(name = "order_count_id")
    private Long id;

    private Long teaId;

    private Integer count;

    private ZonedDateTime orderDate;

    @JsonIgnore
    @OneToMany(mappedBy = "orderCount", cascade = CascadeType.ALL)
    private List<TeaOrder> teaOrders = new ArrayList<>();

    public static OrderCount createTeaOrderCount(Tea tea, ZonedDateTime orderDate){
        OrderCount orderCount = new OrderCount();
        orderCount.setCount(0);
        orderCount.setTeaId(tea.getId());
        orderCount.setOrderDate(orderDate);
        return orderCount;
    }

    public void addTeaOrder(TeaOrder teaOrder){
        teaOrders.add(teaOrder);
        teaOrder.setOrderCount(this);
    }

    public void order(Integer quantity){
        this.count += quantity;
    }

    public void cancel(Integer quantity){
        this.count -= quantity;
    }

}