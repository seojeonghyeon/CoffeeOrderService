package com.justin.teaorderservice.order;


import com.justin.teaorderservice.order.TeaOrder;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Order {
    private Long id;
    private String userId;
    private List<TeaOrder> teaOrderList;
}
