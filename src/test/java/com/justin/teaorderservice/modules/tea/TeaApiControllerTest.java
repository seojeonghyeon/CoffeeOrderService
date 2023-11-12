package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.infra.MockMvcTest;
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

    private static final String ROOT = "/api/order/teas";
    private static final String TEA_SEARCH = "/search";

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
}