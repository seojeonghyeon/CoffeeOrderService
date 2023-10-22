package com.justin.teaorderservice.modules.tea;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("C")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Coffee extends Tea{
    private Integer shot;

    public static Coffee createCoffee(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled, Integer shot){
        Coffee coffee = (Coffee) Tea.builder()
                .teaName(teaName)
                .price(price)
                .stockQuantity(stockQuantity)
                .teaImage(teaImage)
                .description(description)
                .disabled(disabled)
                .build();
        coffee.setShot(1);
        return coffee;
    }

    private void setShot(Integer shot){
        this.shot = shot;
    }
}
