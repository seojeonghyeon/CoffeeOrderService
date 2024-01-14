package me.justin.coffeeorderservice.modules.menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuRepositoryExtension {
    Page<MenuSearchDto> search(MenuSearchCondition menuSearchCondition, Pageable pageable);
}
