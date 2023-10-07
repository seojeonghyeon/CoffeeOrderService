package com.justin.teaorderservice.modules.order;


import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.ZonedDateTime;
import java.util.List;

@Table
@Getter
@Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id @GeneratedValue
    private Long id;

    private String userId;

    @Value("${order.pay-status.pending}")
    private String payStatus;

    @OneToMany(mappedBy = "order")
    @OrderBy("teaId")
    private List<TeaOrder> teaOrderList;

    private ZonedDateTime createdAt;

    private Boolean disabled;
}
