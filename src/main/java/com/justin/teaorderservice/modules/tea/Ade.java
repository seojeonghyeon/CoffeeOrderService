package com.justin.teaorderservice.modules.tea;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("A")
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Ade extends Tea{

    public static Ade createAde(String teaName, Integer price, Integer stockQuantity, String teaImage, String description, boolean disabled){
        Ade ade = (Ade) Tea.builder()
                .teaName(teaName)
                .price(price)
                .stockQuantity(stockQuantity)
                .teaImage(teaImage)
                .description(description)
                .disabled(disabled)
                .build();
        return ade;
    }
}
