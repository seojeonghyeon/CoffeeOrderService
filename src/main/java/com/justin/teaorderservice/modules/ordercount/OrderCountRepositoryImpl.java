package com.justin.teaorderservice.modules.ordercount;

import com.justin.teaorderservice.modules.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import static com.justin.teaorderservice.modules.ordercount.QOrderCount.*;

@Repository
public class OrderCountRepositoryImpl extends Querydsl4RepositorySupport implements OrderCountRepositoryCustom{
    public OrderCountRepositoryImpl() {
        super(OrderCount.class);
    }

    @Override
    public OrderCount findByTeaIdAndOrderDate(Long teaId, LocalDate orderDate) {
        return selectFrom(orderCount)
                .where(teaIdEq(teaId).and(orderDateEq(orderDate)))
                .fetchOne();
    }


    private BooleanExpression teaIdEq(Long teaId){
        return teaId != null ? orderCount.teaId.eq(teaId) : null;
    }

    private BooleanExpression orderDateEq(LocalDate orderDate){
        return orderDate != null ? orderCount.orderDate.eq(orderDate) : orderCount.orderDate.eq(LocalDate.now());
    }
}
