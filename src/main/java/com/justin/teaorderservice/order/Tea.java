package com.justin.teaorderservice.order;

import lombok.Data;

@Data
public class Tea {
    private Long id;
    private String teaName;
    private Integer price;
    private Integer quantity;

    public Tea(){
    }

    public Tea(String teaName, Integer price, Integer quantity) {
        this.teaName = teaName;
        this.price = price;
        this.quantity = quantity;
    }
}
