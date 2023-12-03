package com.justin.teaorderservice.modules.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    @Query(
            value = "SELECT T.PRODUCT_ID as id, T.PRODUCT_NAME as productName, T.PRICE as price, T.STOCK_QUANTITY, T.PRODUCT_IMAGE, O.ORDER_COUNT as orderCount " +
                    "FROM PRODUCTS as T " +
                    "LEFT JOIN (" +
                        "SELECT OC.PRODUCT_ID, SUM(OC.COUNT_UP) as ORDER_COUNT " +
                        "FROM PRODUCT_COUNTS as OC " +
                        "WHERE OC.ORDER_DATE BETWEEN :startDate AND :endDate " +
                        "GROUP BY OC.PRODUCT_ID " +
                        "ORDER BY ORDER_COUNT DESC " +
                        "LIMIT :number" +
                    ") as O " +
                    "WHERE T.PRODUCT_ID = O.PRODUCT_ID " +
                    "ORDER BY O.ORDER_COUNT DESC",
            nativeQuery = true)
    List<PopularProductDto> searchPopularDrink(@Param("number") Integer number, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
