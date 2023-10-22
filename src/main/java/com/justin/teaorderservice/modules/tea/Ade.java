package com.justin.teaorderservice.modules.tea;

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
        setDisabled(disabled);
    }

    public static Ade createAde(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled){
        return new Ade(teaName, price, stockQuantity, teaImage, description, disabled);
    }
}
