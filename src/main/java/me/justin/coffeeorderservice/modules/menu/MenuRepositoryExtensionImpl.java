package me.justin.coffeeorderservice.modules.menu;

import me.justin.coffeeorderservice.modules.support.Querydsl4RepositorySupport;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static me.justin.coffeeorderservice.modules.menu.QMenu.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class MenuRepositoryExtensionImpl extends Querydsl4RepositorySupport implements MenuRepositoryExtension {
    public MenuRepositoryExtensionImpl() {
        super(Menu.class);
    }

    @Override
    public Page<MenuSearchDto> search(MenuSearchCondition menuSearchCondition, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(new QMenuSearchDto(menu.id, menu.menuName, menu.price, menu.menuImage, menu.disabled))
                .from(menu)
                .where(
                        menuNameEq(menuSearchCondition.getMenuName()),
                        disabledEq(menuSearchCondition.getDisabled()),
                        priceBetween(menuSearchCondition.getMinPrice(), menuSearchCondition.getMaxPrice())
                )
        );
    }

    private BooleanExpression menuNameEq(String menuName) {
        return hasText(menuName) ? menu.menuName.eq(menuName) : null;
    }

    private BooleanExpression disabledEq(Boolean disabled) {
        return disabled != null ? menu.disabled.eq(disabled) : null;
    }

    private BooleanExpression priceBetween(Integer minPrice, Integer maxPrice) {
        if (minPrice != null && maxPrice != null) {
            return menu.price.goe(minPrice).and(menu.price.loe(maxPrice));
        } else if (minPrice != null) {
            return menu.price.goe(minPrice);
        } else if (maxPrice != null) {
            return menu.price.loe(maxPrice);
        }
        return null;
    }

}
