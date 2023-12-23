package com.justin.teaorderservice.modules.menu;

import com.justin.teaorderservice.modules.category.MenuCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("A")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ade extends Menu {

    public Ade(String menuName, Integer price, Integer stockQuantity, String menuImage, String description, boolean disabled) {
        setMenuName(menuName);
        setPrice(price);
        setStockQuantity(stockQuantity);
        setMenuImage(menuImage);
        setDescription(description);
        setDisabled(disabled);
    }

    public static Ade createAde(String menuName, Integer price, Integer stockQuantity, String menuImage, String description, boolean disabled, MenuCategory... menuCategories){
        Ade ade = new Ade(menuName, price, stockQuantity, menuImage, description, disabled);
        for (MenuCategory menuCategory : menuCategories){
            ade.addMenuCategory(menuCategory);
        }
        return ade;
    }
}
