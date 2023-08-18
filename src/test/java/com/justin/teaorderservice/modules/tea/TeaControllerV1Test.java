package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.infra.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@MockMvcTest
class TeaControllerV1Test {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @DisplayName("고객 주문 화면 View")
    @Test
    void itemPurchaseForm() throws Exception {
        mockMvc.perform(get("/view/order/v1/teas"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("order/v1/addItems"))
                .andExpect(model().attributeExists("itemPurchaseForm"))
                .andExpect(unauthenticated());
    }

    @DisplayName("Tea 설명 화면 View")
    @Test
    void tea() throws Exception {
        mockMvc.perform(get("/view/order/v1/teas/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("order/v1/tea"))
                .andExpect(model().attributeExists("tea"))
                .andExpect(unauthenticated());
    }

    @DisplayName("고객 주문 처리 - 입력 값 정상")
    @Test
    void itemPurchaseForm_with_correct_input() throws Exception {
        String userId = UUID.randomUUID().toString();

        mockMvc.perform(post("/view/order/v1/teas")
                        .param("userId", userId)
                        .param("itemOrderFormList[0].id","1")
                        .param("itemOrderFormList[0].teaName", "Americano(Hot)")
                        .param("itemOrderFormList[0].price","2000")
                        .param("itemOrderFormList[0].quantity","10000")
                        .param("itemOrderFormList[0].orderQuantity","1")
                        .param("itemOrderFormList[1].id","1")
                        .param("itemOrderFormList[1].teaName", "Americano(Ice)")
                        .param("itemOrderFormList[1].price","2000")
                        .param("itemOrderFormList[1].quantity","10")
                        .param("itemOrderFormList[1].orderQuantity","1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/order/v1/teas/{orderId}/detail"))
                .andExpect(unauthenticated());
    }

    @DisplayName("고객 주문 처리 - 입력 값 오류")
    @Test
    void itemPurchaseForm_with_wrong_input() throws Exception {
        String userId = UUID.randomUUID().toString();

        mockMvc.perform(post("/view/order/v1/teas")
                        .param("userId", userId)
                        .param("itemOrderFormList[0].id","1")
                        .param("itemOrderFormList[0].teaName", "Americano(Hot)")
                        .param("itemOrderFormList[0].price","2000")
                        .param("itemOrderFormList[0].quantity","10000")
                        .param("itemOrderFormList[0].orderQuantity","1")
                        .param("itemOrderFormList[1].id","1")
                        .param("itemOrderFormList[1].teaName", "Americano(Ice)")
                        .param("itemOrderFormList[1].price","2000")
                        .param("itemOrderFormList[1].quantity","10")
                        .param("itemOrderFormList[1].orderQuantity","10000")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("order/v1/addItems"))
                .andExpect(unauthenticated());
    }

    @DisplayName("고객 주문 내역 화면 View")
    @Test
    void orderDetail() throws Exception {
        mockMvc.perform(get("/view/order/v1/teas/1/detail"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("order/v1/order"))
                .andExpect(model().attributeExists("order"))
                .andExpect(unauthenticated());
    }

}