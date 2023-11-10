package com.justin.teaorderservice.modules;

import com.justin.teaorderservice.infra.MockMvcTest;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.QMember;
import com.justin.teaorderservice.modules.member.WithAccount;
import com.justin.teaorderservice.modules.tea.Ade;
import com.justin.teaorderservice.modules.tea.Coffee;
import com.justin.teaorderservice.modules.tea.TeaPack;
import com.justin.teaorderservice.modules.tea.TeaRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static com.justin.teaorderservice.modules.member.QMember.member;
import static org.assertj.core.api.Assertions.*;

@MockMvcTest
@Slf4j
@Transactional
public class QuerydslTest {

    @Autowired private EntityManager entityManager;
    JPAQueryFactory queryFactory;

    private static final String EMAIL = "seojeonghyeon0630@gmail.com";
    private static final String PASSWORD = "SEOjh1234!";
    private static final String SIMPLE_PASSWORD = "1234";

    @Before
    public void before(){
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @WithAccount(
            email = EMAIL,
            password = PASSWORD,
            simplePassword = SIMPLE_PASSWORD
    )
    @Test
    public void querydsl(){
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.email.eq(EMAIL))
                .fetchOne();
        assertThat(findMember.getEmail()).isEqualTo(EMAIL);
    }
}
