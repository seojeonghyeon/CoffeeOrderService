package com.justin.teaorderservice.modules;

import com.justin.teaorderservice.infra.MockMvcTest;
import com.justin.teaorderservice.modules.authority.QAuthority;
import com.justin.teaorderservice.modules.category.Category;
import com.justin.teaorderservice.modules.category.QCategory;
import com.justin.teaorderservice.modules.member.Member;
import com.justin.teaorderservice.modules.member.QMember;
import com.justin.teaorderservice.modules.member.WithAccount;
import com.justin.teaorderservice.modules.tea.*;
import com.justin.teaorderservice.modules.tea.QTea;
import com.justin.teaorderservice.modules.teacategory.QTeaCategory;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.justin.teaorderservice.modules.authority.QAuthority.*;
import static com.justin.teaorderservice.modules.category.QCategory.*;
import static com.justin.teaorderservice.modules.member.QMember.member;
import static com.justin.teaorderservice.modules.tea.QTea.*;
import static com.justin.teaorderservice.modules.teacategory.QTeaCategory.*;
import static com.querydsl.jpa.JPAExpressions.*;
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

    @WithAccount(
            email = EMAIL,
            password = PASSWORD,
            simplePassword = SIMPLE_PASSWORD
    )
    @Test
    public void querydsl(){
        queryFactory = new JPAQueryFactory(entityManager);
        Member findMember = queryFactory
                .select(member)
                .from(member)
                .where(member.email.eq(EMAIL))
                .fetchOne();
        assertThat(findMember.getEmail()).isEqualTo(EMAIL);
    }

    /**
     * from 절 Sub Query 한계
     * from 절 Sub Query(Inline View) 지원 X
     * -> 해결 방안
     * -> 1. Sub Query를 Join으로 변경(가능한 상황이 있고 불가능한 상황도 존재)
     * -> 2. Query를 2번 분리 해서 실행(Query 2번 나가는 게 괜찮은 지 확인 필요, 1개의 요청 당 2번의 Query)
     * -> 3. nativeSQL 사용
     *
     * from 절 안에 from 절 안에 from 절... -> 안 좋은 케이스(화면 케이스에 맞추려 하지 말자)
     * 데이터베이스에서 데이터를 최소화 해서 퍼오자
     *
     * -> Admin 일 경우 -> Query를 복잡하게 만들지 말고 여러 번에 나눠서 가져오자.
     * -> 실 시간 트래픽이 중요한 상황인 경우 -> 이미 캐시가 밑 바침 되어 있다.
     */
    @DisplayName("Fetch Join Test")
    @Test
    public void fetchJoin(){
        queryFactory = new JPAQueryFactory(entityManager);
        Category findCategory = queryFactory.selectFrom(category)
                .where(category.name.eq("Coffee"))
                .fetchOne();

        List<Tea> teas = queryFactory.selectFrom(tea)
                .join(tea.teaCategories, teaCategory).fetchJoin()
                .where(teaCategory.category.eq(findCategory))
                .fetch();
        assertThat(teas)
                .extracting("teaName")
                .containsExactly("Americano(Hot)", "Americano(Ice)", "Caffe Latte(Hot)", "Caffe Latte(Ice)");
    }

    /**
     * Tuple은 querydsl.core 패키지 내 존재
     * -> Repository 계층 안에서 끝내게 끔하고 외부로 나올 땐 DTO로 변환하여 사용
     */
    @DisplayName("Sub Query Test")
    @Test
    public void subQuery(){
        queryFactory = new JPAQueryFactory(entityManager);
        List<String> teas = queryFactory.select(tea.teaName)
                .from(tea)
                .join(tea.teaCategories, teaCategory)
                .where(
                        teaCategory.category.id.eq
                                (
                                        select(category.id)
                                                .from(category)
                                                .where(
                                                        category.name.eq("Coffee")
                                                )
                                )
                )
                .fetch();

        assertThat(teas)
                .containsExactly("Americano(Hot)", "Americano(Ice)", "Caffe Latte(Hot)", "Caffe Latte(Ice)");
    }
}
