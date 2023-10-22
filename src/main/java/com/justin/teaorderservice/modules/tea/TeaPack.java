package com.justin.teaorderservice.modules.tea;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("T")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TeaPack extends Tea{
    public static TeaPack createTeaPack(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled){
        TeaPack teaPack = (TeaPack) Tea.builder()
                .teaName(teaName)
                .price(price)
                .stockQuantity(stockQuantity)
                .teaImage(teaImage)
                .description(description)
                .disabled(disabled)
                .build();
        return teaPack;
    }
}
