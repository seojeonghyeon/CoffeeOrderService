package com.justin.teaorderservice.modules.order;

import lombok.Data;

@Data
public class Tea {
    private Long id;
    private String teaName;
    private Integer price;
    private Integer quantity;
    private String teaImage;
    private String description;

    public Tea(){
    }

    public Tea(String teaName, Integer price, Integer quantity, String teaImage, String description) {
        this.teaName = teaName;
        this.price = price;
        this.quantity = quantity;
        this.teaImage = teaImage;
        this.description = description;
    }
}
