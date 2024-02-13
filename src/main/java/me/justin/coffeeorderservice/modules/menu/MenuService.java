package me.justin.coffeeorderservice.modules.menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {
    private static final Integer POPULAR_MENU_NUMBER = 3;
    private static final Integer POPULAR_MENU_DURING_DAYS = 7;

    private final MenuRepository menuRepository;

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public Menu findById(Long id) {
        return menuRepository.findById(id).orElse(null);
    }

    public Page<MenuSearchDto> search(MenuSearchCondition menuSearchCondition, Pageable pageable){
        return menuRepository.search(menuSearchCondition, pageable);
    }

    public List<PopularMenuDto> searchPopularMenus(){
        LocalDate nowDate = LocalDate.now();
        List<PopularMenuDto> popularMenuDtos = menuRepository.searchPopularDrink(POPULAR_MENU_NUMBER, nowDate.minusDays(POPULAR_MENU_DURING_DAYS), nowDate);
        return popularMenuDtos;
    }
}
