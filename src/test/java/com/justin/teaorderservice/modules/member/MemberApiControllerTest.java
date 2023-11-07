package com.justin.teaorderservice.modules.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.justin.teaorderservice.infra.MockMvcTest;
import com.justin.teaorderservice.infra.auth.JwtTokenProvider;
import com.justin.teaorderservice.modules.login.LoginService;
import com.justin.teaorderservice.modules.member.request.RequestMemberSave;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Slf4j
@MockMvcTest
class MemberApiControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private MockMvc mockMvc;
    @Autowired private MemberRepository memberRepository;
    @Autowired private LoginService loginService;
    @Autowired private ObjectMapper objectMapper;

    private static final String ROOT = "/api/order/members";
    private static final String ADD_MEMBER = "/add";
    private static final String ADD_MEMBER_RESULT = "/detail";

    private static final String EMAIL = "seojeonghyeon0630@gmail.com";
    private static final String PASSWORD = "SEOjh1234!";
    private static final String SIMPLE_PASSWORD = "1234";

    @BeforeEach
    void beforeEach(){

    }

    @AfterEach
    void afterEach(){
        memberRepository.deleteAll();
    }

    @DisplayName("회원 가입 양식")
    @Test
    void addMemberForm() throws Exception{
        mockMvc
                .perform(
                        get(ROOT + ADD_MEMBER)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("email").isEmpty())
                .andExpect(jsonPath("password").isEmpty())
                .andExpect(jsonPath("simplePassword").isEmpty());
    }

    @DisplayName("회원 가입 - 입력값 정상")
    @Test
    void addMember_with_correct_input() throws Exception{
        log.info("Account: " + SecurityContextHolder.getContext().getAuthentication());

        RequestMemberSave requestMemberSave = RequestMemberSave.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .simplePassword(SIMPLE_PASSWORD)
                .build();

        mockMvc
                .perform(
                        post(ROOT + ADD_MEMBER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestMemberSave))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk());

        Member member = memberRepository.findByEmail(EMAIL).orElse(null);
        assertFalse(Objects.requireNonNull(member).getDisabled());
        assertNotNull(loginService.login(EMAIL, PASSWORD));
    }

    @WithAccount(
            email = EMAIL,
            password = PASSWORD,
            simplePassword = SIMPLE_PASSWORD
    )
    @DisplayName("회원 가입 - 입력값 오류 : 존재 하는 Email")
    @Test
    void addMember_with_wrong_input1() throws Exception{
        log.info("Account: " + SecurityContextHolder.getContext().getAuthentication());
        String password = "SEOjh4321!";
        String simplePassword = "4321";

        RequestMemberSave requestMemberSave = RequestMemberSave.builder()
                .email(EMAIL)
                .password(password)
                .simplePassword(simplePassword)
                .build();

        mockMvc
                .perform(
                        post(ROOT + ADD_MEMBER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestMemberSave))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("EXIST_EMAIL"));

        Member member = memberRepository.findByEmail(EMAIL).orElse(null);
        assertFalse(Objects.requireNonNull(member).getDisabled());
        assertNull(loginService.login(EMAIL, password));
    }

    @DisplayName("회원 가입 - 입력값 오류 : simplePassword 미 입력")
    @Test
    void addMember_with_wrong_input2() throws Exception{
        String simplePassword = "";

        RequestMemberSave requestMemberSave = RequestMemberSave.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .simplePassword(simplePassword)
                .build();

        mockMvc
                .perform(
                        post(ROOT + ADD_MEMBER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestMemberSave))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("simplePassword").exists());

        Member member = memberRepository.findByEmail(EMAIL).orElse(null);
        assertNull(member);
    }

    @WithAccount(
            email = EMAIL,
            password = PASSWORD,
            simplePassword = SIMPLE_PASSWORD
    )
    @DisplayName("회원 가입 여부 확인 - 정상")
    @Test
    void memberDetail() throws Exception{
        log.info("Account: " + SecurityContextHolder.getContext().getAuthentication());

        MvcResult mvcResult = mockMvc
                .perform(
                        get(ROOT + "/" + EMAIL + ADD_MEMBER_RESULT)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String content = response.getContentAsString();
        Assertions.assertThat(content.contains(EMAIL)).isTrue();
    }

    @DisplayName("회원 가입 여부 확인 - 오류")
    @Test
    void memberDetail_unauthorized() throws Exception{
        log.info("Account: " + SecurityContextHolder.getContext().getAuthentication());

        mockMvc
                .perform(
                        get(ROOT + "/" + EMAIL + ADD_MEMBER_RESULT)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode").value("NO_EXIST_EMAIL"));
    }
}