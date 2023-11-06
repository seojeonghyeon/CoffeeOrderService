package com.justin.teaorderservice.modules.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justin.teaorderservice.infra.MockMvcTest;
import com.justin.teaorderservice.infra.auth.JwtTokenProvider;
import com.justin.teaorderservice.modules.login.request.RequestLogin;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.MemberRepository;
import com.justin.teaorderservice.modules.member.WithAccount;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@MockMvcTest
class OrderLoginApiControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private MockMvc mockMvc;
    @Autowired private MemberRepository memberRepository;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private ObjectMapper objectMapper;

    private static final String ROOT = "/api/order/login";

    @BeforeEach
    void beforeEach(){

    }

    @AfterEach
    void afterEach(){
        memberRepository.deleteAll();
    }

    @WithAccount(
            email = "seojeonghyeon0630@gmail.com",
            password = "SEOjh1234!",
            simplePassword = "1234"
    )
    @DisplayName("Order 로그인 - 정상")
    @Test
    void login_with_correct_input() throws Exception{

        String email = "seojeonghyeon0630@gmail.com";
        String password = "SEOjh1234!";

        RequestLogin requestLogin = RequestLogin.builder()
                .email(email)
                .password(password)
                .build();

        MvcResult mvcResult = mockMvc
                .perform(
                        post(ROOT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestLogin))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String getToken = response.getContentAsString();
        String getUserId = jwtTokenProvider.getUserId(getToken);
        Member member = memberRepository.findByEmail(email).orElse(null);

        assertThat(getUserId.equals(member.getId())).isTrue();
    }

    @WithAccount(
            email = "seojeonghyeon0630@gmail.com",
            password = "SEOjh1234!",
            simplePassword = "1234"
    )
    @DisplayName("Order 로그인 - 오류 : 잘못된 비밀번호")
    @Test
    void login_with_wrong_input1() throws Exception{

        String email = "seojeonghyeon0630@gmail.com";
        String password = "SEOjh4321!";

        RequestLogin requestLogin = RequestLogin.builder()
                .email(email)
                .password(password)
                .build();

        mockMvc
                .perform(
                        post(ROOT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestLogin))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("LOGIN_FAIL"));
    }

    @WithAccount(
            email = "seojeonghyeon0630@gmail.com",
            password = "SEOjh1234!",
            simplePassword = "1234"
    )
    @DisplayName("Order 로그인 - 오류 : 비밀번호 누락")
    @Test
    void login_with_wrong_input2() throws Exception{

        String email = "seojeonghyeon0630@gmail.com";
        String password = "";

        RequestLogin requestLogin = RequestLogin.builder()
                .email(email)
                .password(password)
                .build();

        mockMvc
                .perform(
                        post(ROOT)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestLogin))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("password").exists());
    }

}