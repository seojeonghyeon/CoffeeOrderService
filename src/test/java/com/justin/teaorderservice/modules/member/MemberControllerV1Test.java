package com.justin.teaorderservice.modules.member;

import com.justin.teaorderservice.infra.MockMvcTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class MemberControllerV1Test {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @DisplayName("고객 주문 화면 View")
    @Test
    void itemPurchaseForm() throws Exception {
        mockMvc.perform(get("/view/order/v1/members/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("members/v1/addMember"))
                .andExpect(model().attributeExists("memberSaveForm"))
                .andExpect(unauthenticated());
    }

    @DisplayName("회원 가입 처리 - 입력 값 정상")
    @Test
    void addMember_with_correct_input() throws Exception{
        mockMvc.perform(post("/view/order/v1/members/add")
                        .param("phoneNumber","01012341235")
                        .param("simplePassword","1234")
                        .param("password","SEOjh1234!")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/order/v1/members/{userId}/detail"))
                .andExpect(unauthenticated());
    }

    @DisplayName("회원 가입 처리 - 입력 값 오류")
    @Test
    void addMember_with_wrong_input() throws Exception{
        mockMvc.perform(post("/view/order/v1/members/add")
                        .param("phoneNumber","01012341235")
                        .param("simplePassword","123")
                        .param("password","SEOjh1234!")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("members/v1/addMember"))
                .andExpect(unauthenticated());
    }

    @DisplayName("회원 가입 정보 표시")
    @Test
    void memberDetail() throws Exception{

        Authority authority = Authority.builder()
                .authorityName("USER")
                .build();

        String uuid = UUID.randomUUID().toString();
        Member member = Member.builder()
                .memberId(uuid)
                .password(passwordEncoder.encode("SEOjh1234!"))
                .simplePassword(passwordEncoder.encode("1234"))
                .phoneNumber("01012341234")
                .createDate(LocalDateTime.now())
                .disabled(false)
                .point(Integer.valueOf(0))
                .authorities(Collections.singleton(authority))
                .build();
        memberService.save(member);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("/order/v1/members/");
        stringBuilder.append(uuid);
        stringBuilder.append("/detail");
        String url = stringBuilder.toString();

        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("members/v1/addResult"))
                .andExpect(model().attributeExists("phoneNumber"))
                .andExpect(unauthenticated());
    }

}