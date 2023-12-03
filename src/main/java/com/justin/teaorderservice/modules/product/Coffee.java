package com.justin.teaorderservice.modules.product;

import com.justin.teaorderservice.modules.category.ProductCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("C")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coffee extends Product {
    private Integer shot;

    private Coffee(String drinkName, Integer price, Integer stockQuantity, String drinkImage, String description, boolean disabled, Integer shot) {
        setProductName(drinkName);
        setPrice(price);
        setStockQuantity(stockQuantity);
        setProductImage(drinkImage);
        setDescription(description);
        setDisabled(disabled);
        setShot(shot);
    }

    public static Coffee createCoffee(String drinkName, Integer price, Integer stockQuantity, String drinkImage, String description, boolean disabled, Integer shot, ProductCategory... drinkCategories){
        Coffee coffee = new Coffee(drinkName, price, stockQuantity, drinkImage, description, disabled, shot);
        for (ProductCategory productCategory : drinkCategories){
            coffee.addDrinkCategory(productCategory);
        }
        return coffee;
    }

    private void setShot(Integer shot){
        this.shot = shot;
    }
}
