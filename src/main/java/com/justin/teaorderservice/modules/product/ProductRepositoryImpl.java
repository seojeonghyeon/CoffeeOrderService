package com.justin.teaorderservice.modules.product;

import com.justin.teaorderservice.modules.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import static org.springframework.util.StringUtils.*;
import static com.justin.teaorderservice.modules.product.QProduct.*;

@Repository
public class ProductRepositoryImpl extends Querydsl4RepositorySupport implements ProductRepositoryCustom {
    public ProductRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public Page<ProductSearchDto> search(ProductSearchCondition productSearchCondition, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(new QProductSearchDto(product.id, product.productName, product.price, product.productImage, product.disabled))
                .from(product)
                .where(
                        productNameEq(productSearchCondition.getProductName()),
                        disabledEq(productSearchCondition.getDisabled()),
                        priceBetween(productSearchCondition.getMinPrice(), productSearchCondition.getMaxPrice())
                )
        );
    }

    private BooleanExpression productNameEq(String productName) {
        return hasText(productName) ? product.productName.eq(productName) : null;
    }

    private BooleanExpression disabledEq(Boolean disabled) {
        return disabled != null ? product.disabled.eq(disabled) : null;
    }

    private BooleanExpression priceBetween(Integer minPrice, Integer maxPrice) {
        if (minPrice != null && maxPrice != null) {
            return product.price.goe(minPrice).and(product.price.loe(maxPrice));
        } else if (minPrice != null) {
            return product.price.goe(minPrice);
        } else if (maxPrice != null) {
            return product.price.loe(maxPrice);
        }
        return null;
    }

}
