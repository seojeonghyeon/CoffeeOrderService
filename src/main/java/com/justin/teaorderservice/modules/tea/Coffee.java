package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.modules.teacategory.TeaCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("C")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coffee extends Tea{
    private Integer shot;

    private Coffee(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, Integer shot) {
        setTeaName(teaName);
        setPrice(price);
        setStockQuantity(stockQuantity);
        setTeaImage(teaImage);
        setDescription(description);
        setDisabled(disabled);
        setShot(shot);
    }

    public static Coffee createCoffee(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, Integer shot, TeaCategory... teaCategories){
        Coffee coffee = new Coffee(teaName, price, stockQuantity, teaImage, description, disabled, shot);
        for (TeaCategory teaCategory : teaCategories){
            coffee.addTeaCategory(teaCategory);
        }
        return coffee;
    }

    private void setShot(Integer shot){
        this.shot = shot;
    }
}
