package me.justin.coffeeorderservice.modules.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, MenuRepositoryExtension {
    @Query(
            value = "SELECT T.MENU_ID as id, T.MENU_NAME as menuName, T.PRICE as price, T.STOCK_QUANTITY as stockQuantity, T.MENU_IMAGE as menuImage, O.ORDER_COUNT as orderCount " +
                    "FROM MENUS as T " +
                    "LEFT JOIN (" +
                        "SELECT OC.MENU_ID, SUM(OC.COUNT_UP) as ORDER_COUNT " +
                        "FROM PRODUCT_ORDER_COUNTS as OC " +
                        "WHERE OC.ORDER_DATE BETWEEN :startDate AND :endDate " +
                        "GROUP BY OC.MENU_ID " +
                        "ORDER BY ORDER_COUNT DESC " +
                        "LIMIT :number" +
                    ") as O " +
                    "WHERE T.MENU_ID = O.MENU_ID " +
                    "ORDER BY O.ORDER_COUNT DESC",
            nativeQuery = true)
    List<PopularMenuDto> searchPopularDrink(@Param("number") Integer number, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
