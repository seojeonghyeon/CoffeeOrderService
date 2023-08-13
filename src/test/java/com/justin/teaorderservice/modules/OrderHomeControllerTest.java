package com.justin.teaorderservice.modules;

import com.justin.teaorderservice.infra.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class OrderHomeControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @DisplayName("Order Home 화면 보이는 지 테스트")
    @Test
    void orderHome() throws Exception{
        mockMvc.perform(get("/order/v1/home"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("order/v1/home"))
                .andExpect(unauthenticated());
    }

}