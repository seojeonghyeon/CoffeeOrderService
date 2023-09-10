package com.justin.teaorderservice.modules.login;

import com.justin.teaorderservice.infra.MockMvcTest;
import com.justin.teaorderservice.infra.session.SessionConst;
import com.justin.teaorderservice.modules.member.Authority;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockMvcTest
class OrderLoginControllerV1Test {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @Autowired
    MemberService memberService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @DisplayName("Order Login 화면 보이는 지 테스트")
    @Test
    void loginForm() throws Exception{
        mockMvc.perform(get("/order/v1/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login/v1/simpleLoginForm"))
                .andExpect(model().attributeExists("simpleLoginForm"))
                .andExpect(unauthenticated());
    }

    @DisplayName("Order Login 처리 - 입력 값 정상")
    @Test
    void simpleLoginV1_with_correct_input() throws Exception{
        Authority authority = Authority.builder()
                .authorityName("USER")
                .build();

        Member member = Member.builder()
                .userId(UUID.randomUUID().toString())
                .password(passwordEncoder.encode("SEOjh1234!"))
                .simplePassword(passwordEncoder.encode("1234"))
                .phoneNumber("01012341234")
                .createDate(LocalDateTime.now())
                .disabled(false)
                .point(Integer.valueOf(0))
                .authorities(Collections.singleton(authority))
                .build();
        memberService.save(member);

        mockMvc.perform(post("/order/v1/login")
                .param("phoneNumber","01012341234")
                .param("simplePassword","1234")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/order/v1/teas"))
                .andExpect(unauthenticated());
    }

    @DisplayName("Order Login 처리 - 입력 값 오류")
    @Test
    void simpleLoginV1_with_wrong_input() throws Exception{

        Authority authority = Authority.builder()
                .authorityName("USER")
                .build();

        Member member = Member.builder()
                .userId(UUID.randomUUID().toString())
                .password(passwordEncoder.encode("SEOjh1234!"))
                .simplePassword(passwordEncoder.encode("1234"))
                .phoneNumber("01012341234")
                .createDate(LocalDateTime.now())
                .disabled(false)
                .point(Integer.valueOf(0))
                .authorities(Collections.singleton(authority))
                .build();
        memberService.save(member);

        mockMvc.perform(post("/order/v1/login")
                        .param("phoneNumber","01012341234")
                        .param("simplePassword","123")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("login/v1/simpleLoginForm"))
                .andExpect(unauthenticated());
    }

    @DisplayName("Order Logout 처리")
    @Test
    void logoutV1() throws Exception{

        Authority authority = Authority.builder()
                .authorityName("USER")
                .build();

        Member member = Member.builder()
                .userId(UUID.randomUUID().toString())
                .password(passwordEncoder.encode("SEOjh1234!"))
                .simplePassword(passwordEncoder.encode("1234"))
                .phoneNumber("01012341234")
                .createDate(LocalDateTime.now())
                .disabled(false)
                .point(Integer.valueOf(0))
                .authorities(Collections.singleton(authority))
                .build();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        mockMvc.perform(post("/order/v1/login/logout")
                .session(session)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/order/v1/home"))
                .andExpect(unauthenticated());

    }



}