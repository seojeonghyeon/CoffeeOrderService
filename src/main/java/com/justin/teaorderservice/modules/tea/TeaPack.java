package com.justin.teaorderservice.modules.tea;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("T")
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeaPack extends Tea{
    public static TeaPack createTeaPack(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled){
        return new TeaPack(teaName, price, stockQuantity, teaImage, description, disabled);

    }

    private TeaPack(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled) {
        setTeaName(teaName);
        setPrice(price);
        setStockQuantity(stockQuantity);
        setTeaImage(teaImage);
        setDisabled(disabled);
    }
}
