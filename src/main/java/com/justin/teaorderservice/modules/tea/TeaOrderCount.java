package com.justin.teaorderservice.modules.tea;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tea_order_counts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class TeaOrderCount {

    @Id @GeneratedValue
    @Column(name = "order_count_id")
    private Long id;

    private Long teaId;

    private Integer orderCount;

    @JsonIgnore
    @OneToMany(mappedBy = "teaOrderCount", cascade = CascadeType.ALL)
    private List<TeaOrder> teaOrders = new ArrayList<>();

    public static TeaOrderCount createTeaOrderCount(Tea tea){
        TeaOrderCount teaOrderCount = new TeaOrderCount();
        teaOrderCount.setOrderCount(0);
        teaOrderCount.setTeaId(tea.getId());
        tea.setTeaOrderCount(teaOrderCount);
        return teaOrderCount;
    }

    public void order(Integer quantity){
        this.orderCount += quantity;
    }

    public void cancel(Integer quantity){
        this.orderCount -= quantity;
    }

}