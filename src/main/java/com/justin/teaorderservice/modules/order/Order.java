package com.justin.teaorderservice.modules.order;


import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder @AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private String userId;
    private String orderId;
    private Boolean disabled;
    private List<TeaOrder> teaOrderList;
}
