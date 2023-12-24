package me.justin.coffeeorderservice.modules.point;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.justin.coffeeorderservice.infra.MockMvcTest;
import me.justin.coffeeorderservice.infra.auth.JwtTokenProvider;
import me.justin.coffeeorderservice.modules.member.MemberRepository;
import me.justin.coffeeorderservice.modules.member.WithAccount;
import me.justin.coffeeorderservice.modules.vo.RequestAddPoint;
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

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@MockMvcTest
class PointApiControllerTest {


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired private MockMvc mockMvc;
    @Autowired private PointRepository pointRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private JwtTokenProvider jwtTokenProvider;
    @Autowired private ObjectMapper objectMapper;

    private static final String ROOT = "/api/order/points";
    private static final String ADD = "/add";
    private static final String RESULT_DETAIL = "/{pointId}/detail";

    private static final String EMAIL = "seojeonghyeon0630@gmail.com";
    private static final String PASSWORD = "SEOjh1234!";
    private static final String SIMPLE_PASSWORD = "1234";

    @BeforeEach
    void beforeEach(){
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
    @DisplayName("Point 충전 양식")
    @Test
    void points() throws Exception{
        String token = jwtTokenProvider.createToken(EMAIL);
        mockMvc
                .perform(
                        get(ROOT + ADD)
                                .header("Authorization","Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("point").value(0))
                .andExpect(jsonPath("addPoint").value(0));
    }

    @WithAccount(
            email = EMAIL,
            password = PASSWORD,
            simplePassword = SIMPLE_PASSWORD
    )
    @DisplayName("Point 충전 - 정상 입력")
    @Test
    void addPoint_with_correct_input() throws Exception{
        String token = jwtTokenProvider.createToken(EMAIL);
        Integer currentPoint = 0;
        Integer addPoint = 10000;

        RequestAddPoint requestAddPoint = RequestAddPoint.createRequestAddPoint(currentPoint, addPoint);

        MvcResult mvcResult = mockMvc
                .perform(
                        post(ROOT + ADD)
                                .header("Authorization","Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAddPoint))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Long addPointId = Long.valueOf(response.getContentAsString());

        Point point = pointRepository.findById(addPointId).orElse(null);
        assertThat(addPoint.equals(Objects.requireNonNull(point).getAddPoint())).isTrue();
    }

    @WithAccount(
            email = EMAIL,
            password = PASSWORD,
            simplePassword = SIMPLE_PASSWORD
    )
    @DisplayName("Point 충전 - 오류 입력(중복 요청 시)")
    @Test
    void addPoint_with_wrong_input() throws Exception{
        /*
         * 첫번째 요청
         */
        String token = jwtTokenProvider.createToken(EMAIL);
        Integer currentPoint = 0;
        Integer addPoint = 10000;
        String resultBody = "No match input point";

        RequestAddPoint requestAddPoint = RequestAddPoint.createRequestAddPoint(currentPoint, addPoint);

        MvcResult mvcResult = mockMvc
                .perform(
                        post(ROOT + ADD)
                                .header("Authorization","Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAddPoint))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Long addPointId = Long.valueOf(response.getContentAsString());

        Point point = pointRepository.findById(addPointId).orElse(null);
        assertThat(addPoint.equals(Objects.requireNonNull(point).getAddPoint())).isTrue();

        /*
         * 두번째 요청
         */
        mvcResult = mockMvc
                .perform(
                        post(ROOT + ADD)
                                .header("Authorization","Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAddPoint))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isBadRequest()).andReturn();
        response = mvcResult.getResponse();
        String responseData = response.getContentAsString();
        assertThat(resultBody.equals(responseData)).isTrue();
    }

    @WithAccount(
            email = EMAIL,
            password = PASSWORD,
            simplePassword = SIMPLE_PASSWORD
    )
    @DisplayName("Point 충전 결과")
    @Test
    void pointResultDetail() throws Exception{
        String token = jwtTokenProvider.createToken(EMAIL);

        /*
         * Point 내 Id 획득
         */
        Integer currentPoint = 0;
        Integer addPoint = 10000;
        RequestAddPoint requestAddPoint = RequestAddPoint.createRequestAddPoint(currentPoint, addPoint);

        MvcResult mvcResult = mockMvc
                .perform(
                        post(ROOT + ADD)
                                .header("Authorization","Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestAddPoint))
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Long addPointId = Long.valueOf(response.getContentAsString());

        Point point = pointRepository.findById(addPointId).orElse(null);
        assertThat(addPoint.equals(Objects.requireNonNull(point).getAddPoint())).isTrue();

        /*
         * Point Id 조회
         */
        mockMvc
                .perform(
                        get(ROOT + "/" + addPointId + "/detail")
                                .header("Authorization","Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("memberName").exists())
                .andExpect(jsonPath("status").value("CONFIRMED"))
                .andExpect(jsonPath("currentPoint").value(currentPoint))
                .andExpect(jsonPath("addPoint").value(addPoint));
    }

}