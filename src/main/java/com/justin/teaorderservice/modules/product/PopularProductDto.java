package com.justin.teaorderservice.modules.product;

public interface PopularProductDto {
    Long getId();
    String getDrinkName();
    Integer getPrice();
    Integer getStockQuantity();
    String getDrinkImage();
    Integer getOrderCount();
}
