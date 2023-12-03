package com.justin.teaorderservice.modules.product;

import com.justin.teaorderservice.modules.category.ProductCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("A")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ade extends Product {

    public Ade(String drinkName, Integer price, Integer stockQuantity, String drinkImage, String description, boolean disabled) {
        setProductName(drinkName);
        setPrice(price);
        setStockQuantity(stockQuantity);
        setProductImage(drinkImage);
        setDescription(description);
        setDisabled(disabled);
    }

    public static Ade createAde(String drinkName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, ProductCategory... drinkCategories){
        Ade ade = new Ade(drinkName, price, stockQuantity, teaImage, description, disabled);
        for (ProductCategory productCategory : drinkCategories){
            ade.addDrinkCategory(productCategory);
        }
        return ade;
    }
}
