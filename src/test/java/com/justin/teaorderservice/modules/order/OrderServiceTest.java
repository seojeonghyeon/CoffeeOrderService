package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.infra.MockMvcTest;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@MockMvcTest
class OrderServiceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void orderSave(){
        List<TeaOrder> teaOrderList = new ArrayList<>();
        TeaOrder teaOrder = TeaOrder.builder()
                .id(1L)
                .orderQuantity(1)
                .price(1000)
                .quantity(20)
                .teaName("Americano(Hot)")
                .build();

        teaOrderList.add(teaOrder);

        Order order = Order.builder()
                .userId("e31e376e-fe1e-47ab-ac13-3e16e81e0333")
                .teaOrderList(teaOrderList)
                .build();
        Order saveOrder = orderService.save(order);
    }

    @DisplayName("주문 ID 조회")
    @Test
    void findById() throws Exception {
        Order order = orderService.findById(1L);
        assertThat(true)
                .isEqualTo(
                        "e31e376e-fe1e-47ab-ac13-3e16e81e0333"
                                .equals(order.getUserId())
                );

    }

}