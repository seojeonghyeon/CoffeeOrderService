package me.justin.coffeeorderservice.modules.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(
            value = "SELECT * " +
                    "FROM CATEGORY as C " +
                    "ORDER BY C.CATEGORY_ID ASC",
            nativeQuery = true
    )
    List<Category> findAllOrderByIdAsc();
}
