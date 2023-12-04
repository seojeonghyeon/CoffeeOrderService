package com.justin.teaorderservice.modules.menu;

import com.justin.teaorderservice.modules.category.MenuCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("T")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tea extends Menu {
    public static Tea createTea(String menuName, Integer price, Integer stockQuantity, String menuImage, String description, boolean disabled, MenuCategory... menuCategories){
        Tea tea = new Tea(menuName, price, stockQuantity, menuImage, description, disabled);
        for (MenuCategory menuCategory : menuCategories){
            tea.addMenuCategory(menuCategory);
        }
        return tea;
    }

    private Tea(String menuName, Integer price, Integer stockQuantity, String menuImage, String description, boolean disabled) {
        setMenuName(menuName);
        setPrice(price);
        setStockQuantity(stockQuantity);
        setMenuImage(menuImage);
        setDescription(description);
        setDisabled(disabled);
    }
}
