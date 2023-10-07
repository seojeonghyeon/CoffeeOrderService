package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.infra.MockMvcTest;
import com.justin.teaorderservice.modules.teaorder.TeaOrder;
import com.justin.teaorderservice.modules.teaorder.TeaOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@MockMvcTest
class OrderServiceTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TeaOrderService teaOrderService;

    @BeforeEach
    void orderSave(){

        Order order = Order.builder()
                .userId("e31e376e-fe1e-47ab-ac13-3e16e81e0333")
                .disabled(false)
                .createdAt(ZonedDateTime.now())
                .build();

        List<TeaOrder> teaOrderList = new ArrayList<>();
        TeaOrder teaOrder = TeaOrder.builder()
                .id(1L)
                .orderQuantity(1)
                .price(1000)
                .quantity(20)
                .teaName("Americano(Hot)")
                .disabled(false)
                .order()
                .build();

        teaOrderList.add(teaOrder);


        teaOrderList.forEach(getTeaOrder -> teaOrderService.save(teaOrder));
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

    @Test
    void proxyCheck(){
        log.info("aop class = {}", orderService.getClass());
        assertThat(AopUtils.isAopProxy(orderService)).isTrue();
    }

}