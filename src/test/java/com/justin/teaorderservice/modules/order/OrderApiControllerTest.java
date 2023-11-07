package com.justin.teaorderservice.modules.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justin.teaorderservice.infra.MockMvcTest;
import com.justin.teaorderservice.infra.auth.JwtTokenProvider;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.member.WithAccount;
import com.justin.teaorderservice.modules.order.request.RequestItemPurchase;
import com.justin.teaorderservice.modules.point.PointRepository;
import com.justin.teaorderservice.modules.point.request.RequestAddPoint;
import com.justin.teaorderservice.modules.tea.TeaRepository;
import com.justin.teaorderservice.modules.teaorder.TeaOrderRepository;
import com.justin.teaorderservice.modules.teaorder.request.RequestItemOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@MockMvcTest
class OrderApiControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private MockMvc mockMvc;
    @Autowired private PointRepository pointRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private OrderRepository orderRepository;
    @Autowired private TeaRepository teaRepository;
    @Autowired private TeaOrderRepository teaOrderRepository;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private ObjectMapper objectMapper;

    public static final String ROOT = "/api/order/orders";
    static final String ORDER_DETAIL = "/{orderId}/detail";

    private static final String EMAIL = "seojeonghyeon0630@gmail.com";
    private static final String PASSWORD = "SEOjh1234!";
    private static final String SIMPLE_PASSWORD = "1234";

    @BeforeEach
    void beforeEach(){
        // TestDataInit.class
        log.info("Account: " + SecurityContextHolder.getContext().getAuthentication());
    }

    @AfterEach
    void afterEach(){
        pointRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @WithAccount(
            email = EMAIL,
            password = PASSWORD,
            simplePassword = SIMPLE_PASSWORD
    )
    @DisplayName("Tea 주문 가능 정보")
    @Test
    void items() throws Exception{
        String token = jwtTokenProvider.createToken(EMAIL);

        mockMvc
                .perform(
                        get(ROOT)
                                .header("Authorization","Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").exists())
                .andExpect(jsonPath("teaOrderList").exists());
    }

    @WithAccount(
            email = EMAIL,
            password = PASSWORD,
            simplePassword = SIMPLE_PASSWORD
    )
    @DisplayName("Tea 주문 - 정상 (잔액 충분)")
    @Test
    void addOrder_with_enough_point() throws Exception{
        String token = jwtTokenProvider.createToken(EMAIL);

        RequestItemPurchase requestItemPurchase = RequestItemPurchase.createRequestItemPurchase(
                RequestItemOrder.createRequestItemOrder(1L, "Americano(Hot)", 2000, 10000, 0),
                RequestItemOrder.createRequestItemOrder(2L, "Americano(Ice)", 2000, 10, 1),
                RequestItemOrder.createRequestItemOrder(3L, "Caffe Latte(Hot)", 2500, 20, 0),
                RequestItemOrder.createRequestItemOrder(4L, "Caffe Latte(Ice)", 2500, 20, 0),
                RequestItemOrder.createRequestItemOrder(5L, "자몽 에이드(Ice)", 3000, 50, 0),
                RequestItemOrder.createRequestItemOrder(6L, "자몽 티(Hot)", 3000, 80, 0)
        );

        /*
         * Point 충전
         */
        Integer currentPoint = 0;
        Integer addPoint = 10000;

        RequestAddPoint requestAddPoint = RequestAddPoint.createRequestAddPoint(currentPoint, addPoint);
        mockMvc
                .perform(
                        post("/api/order/points/add")
                                .header("Authorization","Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAddPoint))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk());

        /*
         * 주문
         */
        MvcResult mvcResult = mockMvc
                .perform(
                        post(ROOT)
                                .header("Authorization","Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestItemPurchase))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Long orderId = Long.valueOf(response.getContentAsString());

        Order order = orderRepository.findById(orderId).orElse(null);
        assertThat(OrderStatus.CONFIRMED == order.getStatus()).isTrue();
    }

    @WithAccount(
            email = EMAIL,
            password = PASSWORD,
            simplePassword = SIMPLE_PASSWORD
    )
    @DisplayName("Tea 주문 - 오류 (잔액 부족)")
    @Test
    void addOrder_with_no_enough_point() throws Exception{
        String token = jwtTokenProvider.createToken(EMAIL);

        RequestItemPurchase requestItemPurchase = RequestItemPurchase.createRequestItemPurchase(
                RequestItemOrder.createRequestItemOrder(1L, "Americano(Hot)", 2000, 10000, 0),
                RequestItemOrder.createRequestItemOrder(2L, "Americano(Ice)", 2000, 10, 1),
                RequestItemOrder.createRequestItemOrder(3L, "Caffe Latte(Hot)", 2500, 20, 0),
                RequestItemOrder.createRequestItemOrder(4L, "Caffe Latte(Ice)", 2500, 20, 0),
                RequestItemOrder.createRequestItemOrder(5L, "자몽 에이드(Ice)", 3000, 50, 0),
                RequestItemOrder.createRequestItemOrder(6L, "자몽 티(Hot)", 3000, 80, 0)
        );

        mockMvc
                .perform(
                        post(ROOT)
                                .header("Authorization","Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestItemPurchase))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("NOT_ENOUGH_POINT"));

        Member member = memberRepository.findByEmail(EMAIL).orElse(null);
        Order order = orderRepository.findByMemberAndStatus(member, OrderStatus.REJECTED).get(0);
        assertThat(order.getTotalPrice().equals(2_000)).isTrue();
    }

    @WithAccount(
            email = EMAIL,
            password = PASSWORD,
            simplePassword = SIMPLE_PASSWORD
    )
    @DisplayName("Tea 주문 정보")
    @Test
    void orderDetail() throws Exception{
        String token = jwtTokenProvider.createToken(EMAIL);

        RequestItemPurchase requestItemPurchase = RequestItemPurchase.createRequestItemPurchase(
                RequestItemOrder.createRequestItemOrder(1L, "Americano(Hot)", 2000, 10000, 0),
                RequestItemOrder.createRequestItemOrder(2L, "Americano(Ice)", 2000, 10, 1),
                RequestItemOrder.createRequestItemOrder(3L, "Caffe Latte(Hot)", 2500, 20, 0),
                RequestItemOrder.createRequestItemOrder(4L, "Caffe Latte(Ice)", 2500, 20, 0),
                RequestItemOrder.createRequestItemOrder(5L, "자몽 에이드(Ice)", 3000, 50, 0),
                RequestItemOrder.createRequestItemOrder(6L, "자몽 티(Hot)", 3000, 80, 0)
        );

        /*
         * Point 충전
         */
        Integer currentPoint = 0;
        Integer addPoint = 10000;

        RequestAddPoint requestAddPoint = RequestAddPoint.createRequestAddPoint(currentPoint, addPoint);
        mockMvc
                .perform(
                        post("/api/order/points/add")
                                .header("Authorization","Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAddPoint))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk());

        /*
         * 주문
         */
        MvcResult mvcResult = mockMvc
                .perform(
                        post(ROOT)
                                .header("Authorization","Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestItemPurchase))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Long orderId = Long.valueOf(response.getContentAsString());

        mockMvc
                .perform(
                        get(ROOT + "/" + orderId + "/detail")
                                .header("Authorization","Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestItemPurchase))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(orderId))
                .andExpect(jsonPath("userName").exists())
                .andExpect(jsonPath("teaOrderList").exists());
    }


}