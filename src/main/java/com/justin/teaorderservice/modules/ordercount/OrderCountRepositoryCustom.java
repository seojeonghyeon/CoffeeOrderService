package com.justin.teaorderservice.modules.ordercount;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public interface OrderCountRepositoryCustom {
    OrderCount findByTeaIdAndOrderDate(Long teaId, LocalDate orderDate);
}
