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
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.justin.teaorderservice.modules.authority.QAuthority.*;
import static com.justin.teaorderservice.modules.category.QCategory.*;
import static com.justin.teaorderservice.modules.member.QMember.member;
import static com.justin.teaorderservice.modules.tea.QTea.*;
import static com.justin.teaorderservice.modules.teacategory.QTeaCategory.*;
import static com.querydsl.jpa.JPAExpressions.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.util.StringUtils.*;

@MockMvcTest
@Slf4j
@Transactional
public class QuerydslTest {

    @PersistenceContext
    private EntityManager entityManager;
    JPAQueryFactory queryFactory;

    private static final String EMAIL = "seojeonghyeon0630@gmail.com";
    private static final String PASSWORD = "SEOjh1234!";
    private static final String SIMPLE_PASSWORD = "1234";

    @BeforeEach
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
        Category findCategory = queryFactory.selectFrom(category)
                .where(category.name.eq("Coffee"))
                .fetchOne();

        List<Tea> teas = queryFactory.selectFrom(tea)
                .join(tea.teaCategories, teaCategory)
                .fetchJoin()
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

    @DisplayName("Dynamic Query Test1")
    @Test
    public void dynamicQuery_BooleanBuilder1(){
        String categoryName = null;
        Integer price = 3_000;

        List<Tea> result = dynamicSearchTeaWithCategoryNameAndPrice1(categoryName, price);
        assertThat(result.size()).isEqualTo(2);
    }

    private List<Tea> dynamicSearchTeaWithCategoryNameAndPrice1(String categoryName, Integer price) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(categoryName != null){
            booleanBuilder.and(
                    teaCategory.category.id.eq(
                            select(category.id)
                                    .from(category)
                                    .where(
                                            category.name.eq(categoryName)
                                    )
                    )
            );
        }
        if(price != null){
            booleanBuilder.and(
                    tea.price.eq(price)
            );
        }

        return queryFactory
                .selectFrom(tea)
                .join(tea.teaCategories, teaCategory)
                .fetchJoin()
                .where(booleanBuilder)
                .fetch();
    }

    @DisplayName("Dynamic Query Test2")
    @Test
    public void dynamicQuery_BooleanBuilder2(){
        String categoryName = "Coffee";
        Integer price = 3_000;

        List<Tea> result = dynamicSearchTeaWithCategoryNameAndPrice2(categoryName, price);
        assertThat(result.size()).isEqualTo(0);
    }

    private List<Tea> dynamicSearchTeaWithCategoryNameAndPrice2(String categoryNameCond, Integer priceCond) {
        return queryFactory
                .selectFrom(tea)
                .join(tea.teaCategories, teaCategory)
                .fetchJoin()
                .where(allEq(categoryNameCond, priceCond))
                .fetch();
    }

    private BooleanExpression allEq(String categoryNameCond, Integer priceCond){
        return categoryNameEq(categoryNameCond).and(priceEq(priceCond));
    }

    private BooleanExpression categoryNameEq(String categoryNameCond) {
        return hasText(categoryNameCond) ? teaCategory.category.id.eq(select(category.id).from(category).where(category.name.eq(categoryNameCond))) : null;
    }

    private BooleanExpression priceEq(Integer priceCond) {
        return priceCond != null ? tea.price.eq(priceCond) : null;
    }

    @DisplayName("Bulk Update Query Test1")
    @Test
    public void bulkUpdateQuery(){
        /**
         * 커피 항목에 대해 10% 할인
         */
        String categoryName = "Coffee";
        double discountPercentage = 10;
        long count = queryFactory
                .update(tea)
                .set(tea.price, tea.price.multiply(100 - discountPercentage).divide(100))
                .where(tea.id.in(
                        select(teaCategory.tea.id)
                                .from(teaCategory)
                                .join(teaCategory.category, category)
                                .where(categoryNameEq(categoryName))
                ))
                .execute();
        /**
         * 영속성 Context가 남아 있어, DB에서 가져 오더라도 무시한 채로 영속성 Context를 그대로 사용
         * -> Bulk 연산 이후엔 영속성 Context를 날려야 한다.
         */
        entityManager.flush();
        entityManager.clear();

        List<Tea> result = dynamicSearchTeaWithCategoryNameAndPrice2(categoryName, 1800);
        assertThat(result.size()).isEqualTo(2);
    }

    /*
     * QueryDSL 내 exist는 count로 존재 여부를 조회한다.
     * public boolean exists(Predicate predicate) {
     * return this.createQuery(predicate).fetchCount() > 0L;
     * }
     * 전체 데이터에 대해 조회 후 결과가 나오기 때문에 성능이 떨어진다.
     *
     * 아래와 같이 fetchFirst를 이용하여 사용하면 fetchFirst 내부 구현 내 limit(1)이 존재해
     * 결과를 1개만 가져와 SQL exist와 성능 차이가 없다.
     *
     */
    private Boolean exist(Long teaId){
        Integer fetchOne = queryFactory
                .selectOne()
                .from(tea)
                .where(tea.id.eq(teaId))
                .fetchFirst();
        return fetchOne != null;
    }
}
