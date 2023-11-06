package com.justin.teaorderservice.modules.tea;

import com.justin.teaorderservice.modules.teacategory.TeaCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("A")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ade extends Tea{

    public Ade(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled) {
        setTeaName(teaName);
        setPrice(price);
        setStockQuantity(stockQuantity);
        setTeaImage(teaImage);
        setDescription(description);
        setDisabled(disabled);
    }

    public static Ade createAde(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, TeaCategory... teaCategories){
        Ade ade = new Ade(teaName, price, stockQuantity, teaImage, description, disabled);
        for (TeaCategory teaCategory : teaCategories){
            ade.addTeaCategory(teaCategory);
        }
        return ade;
    }
}
