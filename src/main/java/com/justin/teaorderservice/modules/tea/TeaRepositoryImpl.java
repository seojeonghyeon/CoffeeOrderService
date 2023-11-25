package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.modules.ordercount.QOrderCount;
import com.justin.teaorderservice.modules.support.Querydsl4RepositorySupport;
import com.justin.teaorderservice.modules.tea.dto.*;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.justin.teaorderservice.modules.ordercount.QOrderCount.*;
import static com.justin.teaorderservice.modules.tea.QTea.*;
import static org.springframework.util.StringUtils.*;

@Repository
public class TeaRepositoryImpl extends Querydsl4RepositorySupport implements TeaRepositoryCustom{
    public TeaRepositoryImpl() {
        super(Tea.class);
    }

    @Override
    public Page<TeaSearchDto> search(TeaSearchCondition teaSearchCondition, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(new QTeaSearchDto(tea.id, tea.teaName, tea.price, tea.teaImage, tea.disabled))
                .from(tea)
                .where(
                        teaNameEq(teaSearchCondition.getTeaName()),
                        disabledEq(teaSearchCondition.getDisabled()),
                        priceBetween(teaSearchCondition.getMinPrice(), teaSearchCondition.getMaxPrice())
                )
        );
    }

    private BooleanExpression teaNameEq(String teaName) {
        return hasText(teaName) ? tea.teaName.eq(teaName) : null;
    }

    private BooleanExpression disabledEq(Boolean disabled) {
        return disabled != null ? tea.disabled.eq(disabled) : null;
    }

    private BooleanExpression priceBetween(Integer minPrice, Integer maxPrice) {
        if(minPrice != null && maxPrice != null){
            return tea.price.goe(minPrice).and(tea.price.loe(maxPrice));
        }else if(minPrice != null){
            return tea.price.goe(minPrice);
        }else if(maxPrice != null){
            return tea.price.loe(maxPrice);
        }else{
            return null;
        }
    }

}
