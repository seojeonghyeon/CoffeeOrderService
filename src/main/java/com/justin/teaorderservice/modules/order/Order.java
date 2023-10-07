package com.justin.teaorderservice.modules.order;


import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import jakarta.persistence.*;
import lombok.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Table
@Getter
@Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private String userId;

    private String status;

    @OneToMany(mappedBy = "order")
    private List<TeaOrder> teaOrderList = new ArrayList<>();

    private ZonedDateTime orderDate;

    private Boolean disabled;

    public Integer getTotalPrice(){
        return teaOrderList.stream().mapToInt(TeaOrder::getTotalPrice).sum();
    }
}
