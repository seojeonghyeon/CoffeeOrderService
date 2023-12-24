package me.justin.coffeeorderservice.modules.order;

import me.justin.coffeeorderservice.modules.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static me.justin.coffeeorderservice.modules.order.QProductOrderCount.*;


@Repository
public class ProductOrderCountRepositoryImpl extends Querydsl4RepositorySupport implements ProductOrderCountRepositoryCustom {
    public ProductOrderCountRepositoryImpl() {
        super(ProductOrderCount.class);
    }

    @Override
    public ProductOrderCount findByProductIdAndOrderDate(Long productId, LocalDate orderDate) {
        return selectFrom(productOrderCount)
                .where(productIdEq(productId).and(orderDateEq(orderDate)))
                .fetchOne();
    }


    private BooleanExpression productIdEq(Long productId){
        return productId != null ? productOrderCount.menuId.eq(productId) : null;
    }

    private BooleanExpression orderDateEq(LocalDate orderDate){
        return orderDate != null ? productOrderCount.orderDate.eq(orderDate) : productOrderCount.orderDate.eq(LocalDate.now());
    }
}
