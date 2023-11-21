package com.justin.teaorderservice.modules.ordercount;

import java.time.ZonedDateTime;
import java.util.List;

public interface OrderCountRepositoryCustom {
    OrderCount findByTeaIdAndOrderDate(Long teaId, ZonedDateTime orderDate);
    List<OrderCountDto> countOfOrdersPerPeriod(Integer number, ZonedDateTime startTime, ZonedDateTime endTime);
}
