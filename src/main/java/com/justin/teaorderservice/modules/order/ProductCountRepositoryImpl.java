package com.justin.teaorderservice.modules.order;

import com.justin.teaorderservice.modules.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.justin.teaorderservice.modules.order.QProductCount.productCount;

@Repository
public class ProductCountRepositoryImpl extends Querydsl4RepositorySupport implements ProductCountRepositoryCustom {
    public ProductCountRepositoryImpl() {
        super(ProductCount.class);
    }

    @Override
    public ProductCount findByProductIdAndOrderDate(Long productId, LocalDate orderDate) {
        return selectFrom(productCount)
                .where(productIdEq(productId).and(orderDateEq(orderDate)))
                .fetchOne();
    }


    private BooleanExpression productIdEq(Long productId){
        return productId != null ? productCount.productId.eq(productId) : null;
    }

    private BooleanExpression orderDateEq(LocalDate orderDate){
        return orderDate != null ? productCount.orderDate.eq(orderDate) : productCount.orderDate.eq(LocalDate.now());
    }
}
