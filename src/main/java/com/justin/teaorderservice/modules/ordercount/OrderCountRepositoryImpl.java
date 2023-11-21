package com.justin.teaorderservice.modules.ordercount;

import com.justin.teaorderservice.modules.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

import static com.justin.teaorderservice.modules.ordercount.QOrderCount.*;

@Repository
public class OrderCountRepositoryImpl extends Querydsl4RepositorySupport implements OrderCountRepositoryCustom{
    public OrderCountRepositoryImpl() {
        super(OrderCount.class);
    }

    @Override
    public OrderCount findByTeaIdAndOrderDate(Long teaId, ZonedDateTime orderDate) {
        return selectFrom(orderCount)
                .where(teaIdEq(teaId).and(orderDateEq(orderDate)))
                .fetchOne();
    }

    @Override
    public List<OrderCountDto> countOfOrdersPerPeriod(Integer number, ZonedDateTime startTime, ZonedDateTime endTime) {
        return select(new QOrderCountDto(orderCount.teaId, orderCount.count.sum()))
                .from(orderCount)
                .where(timeBetween(startTime, endTime))
                .groupBy(orderCount.teaId)
                .orderBy(orderCount.count.sum().desc())
                .limit(number)
                .fetch();
    }

    private BooleanExpression teaIdEq(Long teaId){
        return teaId != null ? orderCount.teaId.eq(teaId) : null;
    }

    private BooleanExpression orderDateEq(ZonedDateTime orderDate){
        return orderDate != null ? orderCount.orderDate.eq(orderDate.withHour(0).withMinute(0).withSecond(0)) : orderCount.orderDate.eq(ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0));
    }

    private BooleanExpression timeBetween(ZonedDateTime startTime, ZonedDateTime endTime) {
        if(startTime != null && endTime != null){
            return orderCount.orderDate.between(startTime, endTime);
        }else if(startTime == null && endTime != null){
            return orderCount.orderDate.between(endTime.minusDays(7), endTime);
        }else{
            return orderCount.orderDate.between(ZonedDateTime.now().minusDays(7), ZonedDateTime.now());
        }
    }
}
