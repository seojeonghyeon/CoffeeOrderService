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

    @Override
    public List<OrderCountDto> countOfOrdersPerPeriod(Integer number, LocalDate startDate, LocalDate endDate) {
        return select(new QOrderCountDto(orderCount.teaId, orderCount.count.sum()))
                .from(orderCount)
                .where(timeBetween(startDate, endDate))
                .groupBy(orderCount.teaId)
                .orderBy(orderCount.count.sum().desc())
                .limit(number)
                .fetch();
    }

    private BooleanExpression teaIdEq(Long teaId){
        return teaId != null ? orderCount.teaId.eq(teaId) : null;
    }

    private BooleanExpression orderDateEq(LocalDate orderDate){
        return orderDate != null ? orderCount.orderDate.eq(orderDate) : orderCount.orderDate.eq(LocalDate.now());
    }

    private BooleanExpression timeBetween(LocalDate startDate, LocalDate endDate) {
        if(startDate != null && endDate != null){
            return orderCount.orderDate.between(startDate, endDate);
        }else if(startDate == null && endDate != null){
            return orderCount.orderDate.between(endDate.minusDays(7), endDate);
        }else{
            return orderCount.orderDate.between(LocalDate.now().minusDays(7), LocalDate.now());
        }
    }
}
