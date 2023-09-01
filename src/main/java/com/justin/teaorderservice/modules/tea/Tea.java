package com.justin.teaorderservice.modules.tea;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Tea {
    private Long id;
    private String teaName;
    private Integer price;
    private Integer quantity;
    private String teaImage;
    private String description;
    private Boolean disabled;

    public Tea(){
    }

    public Tea(String teaName, Integer price, Integer quantity, String teaImage, String description, boolean disabled) {
        this.teaName = teaName;
        this.price = price;
        this.quantity = quantity;
        this.teaImage = teaImage;
        this.description = description;
        this.disabled = disabled;
    }
}
