package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.modules.tea.dto.PopularTeaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface TeaRepository extends JpaRepository<Tea, Long>, TeaRepositoryCustom {
    @Query(
            value = "SELECT T.TEA_ID as id, T.TEA_NAME as teaName, T.PRICE as price, T.STOCK_QUANTITY, T.TEA_IMAGE, O.ORDER_COUNT as orderCount " +
                    "FROM TEAS as T " +
                    "LEFT JOIN (" +
                        "SELECT OC.TEA_ID, SUM(OC.COUNT_UP) as ORDER_COUNT " +
                        "FROM ORDER_COUNTS as OC " +
                        "WHERE :startDate <= OC.ORDER_DATE AND OC.ORDER_DATE <= :endDate " +
                        "GROUP BY OC.TEA_ID " +
                        "ORDER BY ORDER_COUNT DESC " +
                        "LIMIT :number" +
                    ") as O " +
                    "WHERE T.TEA_ID = O.TEA_ID " +
                    "ORDER BY O.ORDER_COUNT DESC",
            nativeQuery = true)
    List<PopularTeaDto> searchPopularTea(@Param("number") Integer number, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
