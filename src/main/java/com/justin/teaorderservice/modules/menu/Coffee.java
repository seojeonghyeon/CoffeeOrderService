package com.justin.teaorderservice.modules.menu;

import com.justin.teaorderservice.modules.category.MenuCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("C")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coffee extends Menu {
    private Integer shot;

    private Coffee(String menuName, Integer price, Integer stockQuantity, String menuImage, String description, boolean disabled, Integer shot) {
        setMenuName(menuName);
        setPrice(price);
        setStockQuantity(stockQuantity);
        setMenuImage(menuImage);
        setDescription(description);
        setDisabled(disabled);
        setShot(shot);
    }

    public static Coffee createCoffee(String menuName, Integer price, Integer stockQuantity, String menuImage, String description, boolean disabled, Integer shot, MenuCategory... menuCategories){
        Coffee coffee = new Coffee(menuName, price, stockQuantity, menuImage, description, disabled, shot);
        for (MenuCategory menuCategory : menuCategories){
            coffee.addMenuCategory(menuCategory);
        }
        return coffee;
    }

    private void setShot(Integer shot){
        this.shot = shot;
    }
}
