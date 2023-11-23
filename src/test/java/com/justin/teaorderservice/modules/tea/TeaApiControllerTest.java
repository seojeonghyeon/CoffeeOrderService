package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.infra.MockMvcTest;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberService;
import com.justin.teaorderservice.modules.member.WithAccount;
import com.justin.teaorderservice.modules.order.OrderService;
import com.justin.teaorderservice.modules.order.request.RequestItemPurchase;
import com.justin.teaorderservice.modules.point.PointService;
import com.justin.teaorderservice.modules.teaorder.request.RequestItemOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@MockMvcTest
class TeaApiControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private MockMvc mockMvc;
    @Autowired private TeaRepository teaRepository;
    @Autowired private MemberService memberService;
    @Autowired private OrderService orderService;
    @Autowired private PointService pointService;

    private static final String ROOT = "/api/order/teas";
    private static final String TEA_SEARCH = "/search";
    private static final String TEA_POPULAR_TOP_THREE_BY_LAST_SEVEN_DAYS = "/popular/teas";

    private static final String EMAIL = "seojeonghyeon0630@gmail.com";
    private static final String PASSWORD = "SEOjh1234!";
    private static final String SIMPLE_PASSWORD = "1234";

    @BeforeEach
    void beforeEach(){
    }

    @AfterEach
    void afterEach(){
        teaRepository.deleteAll();
    }

    @DisplayName("Tea 리스트 확인")
    @Test
    void items() throws Exception{
        mockMvc
                .perform(
                        get(ROOT)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("responseTeaListItems").exists());
    }

    @DisplayName("Tea 상세 정보")
    @Test
    void tea() throws Exception{
        mockMvc
                .perform(
                        get(ROOT + "/1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("teaName").value("Americano(Hot)"))
                .andExpect(jsonPath("price").value(2000))
                .andExpect(jsonPath("quantity").value(10000))
                .andExpect(jsonPath("teaImage").value("https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg"));
    }

    @DisplayName("Tea 동적 검색")
    @Test
    void search() throws Exception{
        mockMvc
                .perform(
                        get(ROOT + TEA_SEARCH+"?minPrice=2300&maxPrice=2900&page=1&size=1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("responseTeaSearchList").exists())
                .andExpect(jsonPath("$.responseTeaSearchList[0].teaName").value("Caffe Latte(Ice)"));
    }

    @WithAccount(
            email = EMAIL,
            password = PASSWORD,
            simplePassword = SIMPLE_PASSWORD
    )
    @DisplayName("인기 메뉴 목록 조회")
    @Test
    void popularTeas() throws Exception{
        RequestItemOrder requestItemOrder1 = RequestItemOrder.createRequestItemOrder(1L, "Americano(Hot)", 2000, 10000, 2);
        RequestItemOrder requestItemOrder2 = RequestItemOrder.createRequestItemOrder(2L, "Americano(Ice)", 2000, 10000, 0);
        RequestItemOrder requestItemOrder3 = RequestItemOrder.createRequestItemOrder(3L, "Americano(Hot)", 2000, 10000, 3);
        RequestItemOrder requestItemOrder4 = RequestItemOrder.createRequestItemOrder(4L, "Americano(Hot)", 2000, 10000, 5);
        RequestItemOrder requestItemOrder5 = RequestItemOrder.createRequestItemOrder(5L, "Americano(Hot)", 2000, 10000, 10);
        RequestItemOrder requestItemOrder6 = RequestItemOrder.createRequestItemOrder(6L, "Americano(Hot)", 2000, 10000, 0);

        RequestItemPurchase requestItemPurchase = RequestItemPurchase.createRequestItemPurchase(requestItemOrder1, requestItemOrder2, requestItemOrder3, requestItemOrder4, requestItemOrder5, requestItemOrder6);

        Member member = memberService.findByEmail(EMAIL);
        pointService.addPoint(member.getId(), 0, 100_000);
        orderService.addOrder(member, requestItemPurchase);

        mockMvc
                .perform(
                        get(ROOT + TEA_POPULAR_TOP_THREE_BY_LAST_SEVEN_DAYS)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("responsePopularTeaList").exists())
                .andExpect(jsonPath("$.responsePopularTeaList[0].id").value(5));
    }
}