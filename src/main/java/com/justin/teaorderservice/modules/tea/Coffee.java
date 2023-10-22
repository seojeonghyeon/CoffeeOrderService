package com.justin.teaorderservice.modules.tea;

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
        setDisabled(disabled);
        setShot(shot);
    }

    public static Coffee createCoffee(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, Integer shot){
        return new Coffee(teaName, price, stockQuantity, teaImage, description, disabled, shot);
    }

    private void setShot(Integer shot){
        this.shot = shot;
    }
}
