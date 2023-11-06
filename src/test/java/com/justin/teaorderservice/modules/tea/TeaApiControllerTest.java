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

    @BeforeEach
    void beforeEach(){
        Coffee coffee = createCoffee("Americano(Hot)", 2000, 10000,
                "https://cdn.paris.spl.li/wp-content/uploads/200406_HOT%E1%84%8B%E1%85%A1%E1%84%86%E1%85%A6%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A1%E1%84%82%E1%85%A9-1280x1280.jpg",
                "에스프레소에 뜨거운 물을 희석시켜 만든 음료", false);
        teaRepository.save(coffee);

        Ade ade = createAde("자몽 에이드(Ice)", 3000, 50,
                "https://cdn.paris.spl.li/wp-content/uploads/2023/06/%EC%9E%90%EB%AA%BD-1276x1280.png",
                "자몽의 과즙에 탄산 따위를 넣어 만든 음료", false);
        teaRepository.save(ade);

        TeaPack teaPack = createTeaPack("자몽 티(Hot)", 3000, 80,
                "https://cdn.paris.spl.li/wp-content/uploads/201008-%E1%84%84%E1%85%A1%E1%84%8C%E1%85%A1%E1%84%86%E1%85%A9%E1%86%BC-1280x1280.jpg",
                "자몽의 과즙에 따끈한 물 따위를 넣어 만든 음료", false);
        teaRepository.save(teaPack);

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


    private Coffee createCoffee(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled){
        return Coffee.createCoffee(teaName, price, stockQuantity, teaImage,description,disabled, 1);
    }

    private TeaPack createTeaPack(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled){
        return TeaPack.createTeaPack(teaName, price, stockQuantity, teaImage,description,disabled);
    }

    private Ade createAde(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled){
        return Ade.createAde(teaName, price, stockQuantity, teaImage,description,disabled);
    }
}