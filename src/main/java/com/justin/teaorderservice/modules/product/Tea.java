package com.justin.teaorderservice.modules.product;

import com.justin.teaorderservice.modules.category.ProductCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("T")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tea extends Product {
    public static Tea createTea(String drinkName, Integer price, Integer stockQuantity, String drinkImage, String description, boolean disabled, ProductCategory... drinkCategories){
        Tea tea = new Tea(drinkName, price, stockQuantity, drinkImage, description, disabled);
        for (ProductCategory productCategory : drinkCategories){
            tea.addDrinkCategory(productCategory);
        }
        return tea;
    }

    private Tea(String drinkName, Integer price, Integer stockQuantity, String drinkImage, String description, boolean disabled) {
        setProductName(drinkName);
        setPrice(price);
        setStockQuantity(stockQuantity);
        setProductImage(drinkImage);
        setDescription(description);
        setDisabled(disabled);
    }
}
