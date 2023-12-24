package me.justin.coffeeorderservice.modules.category;

import me.justin.coffeeorderservice.modules.menu.Menu;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Table(name = "menu_category") @Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MenuCategory {
    @Id
    @GeneratedValue
    @Column(name = "menu_category_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public static MenuCategory createMenuCategory(Category category){
        MenuCategory menuCategory = new MenuCategory();
        menuCategory.setCategory(category);
        return menuCategory;
    }
}
