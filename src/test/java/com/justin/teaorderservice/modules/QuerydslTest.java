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
import static com.justin.teaorderservice.modules.member.QMember.member;
import static com.justin.teaorderservice.modules.tea.QTea.*;
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

    @DisplayName("Fetch Join Test")
    @Test
    public void fetchJoin(){
        queryFactory = new JPAQueryFactory(entityManager);
        Category category = queryFactory.selectFrom(QCategory.category)
                .where(QCategory.category.name.eq("Coffee"))
                .fetchOne();

        List<Tea> teas = queryFactory.selectFrom(QTea.tea)
                .join(QTea.tea.teaCategories, QTeaCategory.teaCategory)
                .where(QTeaCategory.teaCategory.category.eq(category))
                .fetch();

        assertThat(teas)
                .extracting("teaName")
                .containsExactly("Americano(Hot)", "Americano(Ice)", "Caffe Latte(Hot)", "Caffe Latte(Ice)");
    }
}
