package com.justin.teaorderservice.modules.menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuRepositoryCustom {
    Page<MenuSearchDto> search(MenuSearchCondition menuSearchCondition, Pageable pageable);
}
